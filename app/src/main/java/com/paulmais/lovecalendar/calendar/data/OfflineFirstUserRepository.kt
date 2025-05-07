package com.paulmais.lovecalendar.calendar.data

import com.paulmais.lovecalendar.auth.domain.AuthRepository
import com.paulmais.lovecalendar.calendar.data.mapper.toDTO
import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.calendar.domain.model.isPending
import com.paulmais.lovecalendar.calendar.domain.model.pendingSyncInfo
import com.paulmais.lovecalendar.calendar.domain.model.syncedSyncInfo
import com.paulmais.lovecalendar.calendar.domain.repository.LocalMeetingsDataSource
import com.paulmais.lovecalendar.calendar.domain.repository.RemoteUserDataSource
import com.paulmais.lovecalendar.calendar.domain.repository.UserPrefs
import com.paulmais.lovecalendar.calendar.domain.repository.UserRepository
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.Result
import com.paulmais.lovecalendar.core.domain.andThen
import com.paulmais.lovecalendar.core.domain.asEmptyDataResult
import com.paulmais.lovecalendar.core.domain.flatMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDate

class OfflineFirstUserRepository(
    private val userPrefs: UserPrefs,
    private val localMeetingsDataSource: LocalMeetingsDataSource,
    private val remoteUserDataSource: RemoteUserDataSource,
    private val authRepository: AuthRepository,
    private val applicationScope: CoroutineScope
) : UserRepository {

    override fun getMeetings(): Flow<List<LocalDate>> {
        return localMeetingsDataSource.getMeetings()
    }

    override fun getStartingDate(): Flow<LocalDate?> {
        return userPrefs.getStartingDate()
    }

    override fun getSyncStatus(): Flow<SyncStatus> {
        return combine(
            localMeetingsDataSource.getPendingSyncMeetings(),
            userPrefs.getSyncInfo()
        ) { pendingMeetings, syncInfo ->
            when {
                pendingMeetings.isNotEmpty() -> SyncStatus.Pending
                syncInfo.isPending() -> SyncStatus.Pending
                else -> SyncStatus.Synced
            }
        }
    }

    override suspend fun fetchUser(): EmptyResult<DataError> {
        return try {
            val result = applicationScope.async {
                remoteUserDataSource.getUser()
                    .flatMap { userDTO ->
                        userPrefs.updateUserData(
                            id = userDTO.id,
                            name = userDTO.name,
                            email = userDTO.email,
                            startingDate = userDTO.specialDate
                        ).andThen {
                            userPrefs.updateSyncInfo(syncedSyncInfo)
                        }.andThen {
                            localMeetingsDataSource.updateMeetings(
                                meetings = userDTO.meetings.map { LocalDate.parse(it) }
                            )
                        }.andThen {
                            localMeetingsDataSource.updateAllMeetingSyncStatus(SyncStatus.Synced)
                        }.asEmptyDataResult()
                    }
            }.await()

            if (result !is Result.Success) setSyncToPending()
            result
        } catch (e: Exception) {
            setSyncToPending()
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun updateMeetings(meetings: List<LocalDate>): EmptyResult<DataError> {
        return try {
            val result = localMeetingsDataSource.updateMeetings(meetings = meetings)
                .andThen {
                    userPrefs.getUserWithoutMeetings().flatMap { userWithoutMeetings ->
                        val user = userWithoutMeetings.copy(meetings = meetings)
                        remoteUserDataSource.updateUser(user = user.toDTO()).andThen {
                            localMeetingsDataSource.updateAllMeetingSyncStatus(SyncStatus.Synced)
                        }.asEmptyDataResult()
                    }
                }
            if (result !is Result.Success) setSyncToPending()
            result
        } catch (e: Exception) {
            setSyncToPending()
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun updateUserProfile(
        name: String?,
        email: String?,
        specialDate: LocalDate?
    ): EmptyResult<DataError> {
        return try {
            val result = userPrefs.updateUserData(
                name = name,
                email = email,
                startingDate = specialDate?.toString()
            ).andThen {
                userPrefs.getUserWithoutMeetings().flatMap { userWithoutMeetings ->
                    val meetings = getMeetings().first()
                    val user = userWithoutMeetings.copy(meetings = meetings)
                    remoteUserDataSource.updateUser(user = user.toDTO()).andThen {
                        userPrefs.updateSyncInfo(syncedSyncInfo)
                    }.asEmptyDataResult()
                }
            }
            if (result !is Result.Success) setSyncToPending()
            result
        } catch (e: Exception) {
            setSyncToPending()
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun syncWithRemote(): EmptyResult<DataError> {
        return try {
            val userWithoutMeetingResult = userPrefs.getUserWithoutMeetings()
            if (userWithoutMeetingResult is Result.Error && userWithoutMeetingResult.error == DataError.Local.MISSING_DATA) {
                fetchUser()
            }
            userWithoutMeetingResult
                .flatMap { userWithoutMeetings ->
                    val meetings = getMeetings().first()
                    val user = userWithoutMeetings.copy(meetings = meetings)

                    when (remoteUserDataSource.updateUser(user = user.toDTO())) {
                        is Result.Error -> {
                            setSyncToPending().asEmptyDataResult()
                        }

                        is Result.Success -> {
                            userPrefs.updateSyncInfo(syncedSyncInfo).andThen {
                                localMeetingsDataSource.updateAllMeetingSyncStatus(SyncStatus.Synced)
                            }.andThen { fetchUser() }.asEmptyDataResult()
                        }
                    }
                }
        } catch (e: Exception) {
            setSyncToPending()
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun logout(): EmptyResult<DataError> {
        return userPrefs.clearUserData().andThen {
            localMeetingsDataSource.deleteAllMeetings()
        }.andThen {
            authRepository.logout()
        }.asEmptyDataResult()
    }

    private suspend fun setSyncToPending(): EmptyResult<DataError> {
        userPrefs.updateSyncInfo(pendingSyncInfo)
        return localMeetingsDataSource.updateAllMeetingSyncStatus(SyncStatus.Pending)
    }
}