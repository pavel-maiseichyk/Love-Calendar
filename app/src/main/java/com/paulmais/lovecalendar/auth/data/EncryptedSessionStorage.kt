package com.paulmais.lovecalendar.auth.data

import androidx.datastore.core.DataStore
import com.paulmais.lovecalendar.auth.data.mapper.toAuthInfo
import com.paulmais.lovecalendar.auth.data.mapper.toAuthInfoSerializable
import com.paulmais.lovecalendar.auth.data.model.AuthInfoSerializable
import com.paulmais.lovecalendar.auth.domain.SessionStorage
import com.paulmais.lovecalendar.auth.domain.model.AuthInfo
import kotlinx.coroutines.flow.firstOrNull

class EncryptedSessionStorage(
    private val dataStore: DataStore<AuthInfoSerializable>
) : SessionStorage {

    override suspend fun get(): AuthInfo? {
        return dataStore.data.firstOrNull()?.toAuthInfo()
    }

    override suspend fun set(info: AuthInfo?) {
        if (info != null) {
            dataStore.updateData { info.toAuthInfoSerializable() }
        }
    }
}