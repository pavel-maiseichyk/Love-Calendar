package com.paulmais.lovecalendar.calendar.presentation.settings

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.paulmais.lovecalendar.calendar.domain.repository.FakeUserRepository
import com.paulmais.lovecalendar.calendar.domain.util.DateUtil.reverseDateFormat
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.presentation.asUiText
import com.paulmais.lovecalendar.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var userRepository: FakeUserRepository

    @BeforeEach
    fun setUp() {
        userRepository = FakeUserRepository()
        viewModel = SettingsViewModel(userRepository)
    }

    @Test
    fun `test state if starting date exists`() = runTest {
        val date = LocalDate(year = 2022, monthNumber = 2, dayOfMonth = 20)
        val reversedDate = date.reverseDateFormat()
        userRepository.startingDate.emit(date)

        viewModel.state.test {
            val emission1 = awaitItem()
            assertThat(emission1.specialDateString).isEqualTo("")

            val emission2 = awaitItem()
            assertThat(emission2.specialDateString).isEqualTo(reversedDate)
        }
    }

    @Test
    fun `test state if starting date doesn't exist`() = runTest {
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.specialDateString).isEqualTo("")
        }
    }

    @Test
    fun `CancelUpdateDateClicked, state is updated`() = runTest {
        viewModel.onAction(SettingsAction.CancelUpdateDateClicked)
        advanceUntilIdle()
        assertThat(viewModel.state.value.isEditingSpecialDate).isFalse()
        assertThat(viewModel.state.value.specialDateString).isEqualTo("")
    }

    @Test
    fun `BackClicked, event sent`() = runTest {
        viewModel.onAction(SettingsAction.BackClicked)

        viewModel.uiChannel.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(SettingsEvent.NavigateBack)
        }
    }

    @Test
    fun `LogoutClicked, logout successful`() = runTest {
        viewModel.onAction(SettingsAction.LogoutClicked)

        viewModel.uiChannel.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(SettingsEvent.Logout)
        }
    }

    @Test
    fun `LogoutClicked, logout failed`() = runTest {
        val error = DataError.Network.UNKNOWN
        userRepository.error = error

        viewModel.onAction(SettingsAction.LogoutClicked)

        viewModel.uiChannel.test {
            val emission = awaitItem()
            val uiText = (emission as SettingsEvent.ShowMessage).uiText
            assertThat(uiText).isEqualTo(error.asUiText())
        }
    }

    @Test
    fun `DateClicked, state is updated`() = runTest {
        viewModel.onAction(SettingsAction.DateClicked)
        advanceUntilIdle()
        assertThat(viewModel.state.value.isEditingSpecialDate).isTrue()
    }
}