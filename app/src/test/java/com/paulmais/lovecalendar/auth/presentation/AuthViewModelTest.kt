package com.paulmais.lovecalendar.auth.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.paulmais.lovecalendar.auth.domain.FakeAuthRepository
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.presentation.asUiText
import com.paulmais.lovecalendar.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private lateinit var authRepository: FakeAuthRepository

    @BeforeEach
    fun setUp() {
        authRepository = FakeAuthRepository()
        viewModel = AuthViewModel(authRepository)
    }

    @Test
    fun `AuthTypeChanged called`() = runTest {
        val type = viewModel.state.value.authType
        val changedType = AuthType.changeAuthType(type)

        viewModel.onAction(AuthAction.AuthTypeChanged)
        advanceUntilIdle()

        assertThat(viewModel.state.value.authType).isEqualTo(changedType)
    }

    @Test
    fun `LoginChanged called`() = runTest {
        val newLogin = "new@test.com"

        viewModel.onAction(AuthAction.LoginChanged(newLogin))
        advanceUntilIdle()

        assertThat(viewModel.state.value.login).isEqualTo(newLogin)
    }

    @Test
    fun `PasswordChanged called`() = runTest {
        val newPassword = "12345678"

        viewModel.onAction(AuthAction.PasswordChanged(newPassword))
        advanceUntilIdle()

        assertThat(viewModel.state.value.password).isEqualTo(newPassword)
    }

    @Test
    fun `MainButtonClicked called`() = runTest {
        val newPassword = "12345678"

        viewModel.onAction(AuthAction.PasswordChanged(newPassword))
        advanceUntilIdle()

        assertThat(viewModel.state.value.password).isEqualTo(newPassword)
    }

    @Test
    fun `MainButtonClicked called, logging in, success`() = runTest {
        assertThat(viewModel.state.value.authType).isEqualTo(AuthType.Login)

        viewModel.onAction(AuthAction.MainButtonClicked)
        advanceUntilIdle()

        viewModel.events.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(AuthEvent.CompleteSign)
        }
    }

    @Test
    fun `MainButtonClicked called, logging in, failure`() = runTest {
        val error = DataError.Network.UNKNOWN
        assertThat(viewModel.state.value.authType).isEqualTo(AuthType.Login)

        authRepository.error = error
        viewModel.onAction(AuthAction.MainButtonClicked)
        advanceUntilIdle()

        viewModel.events.test {
            val emission = awaitItem()
            val uiText = (emission as AuthEvent.ShowError).error
            assertThat(uiText).isEqualTo(error.asUiText())
        }
    }

    @Test
    fun `MainButtonClicked called, registering, success`() = runTest {
        viewModel.onAction(AuthAction.AuthTypeChanged)
        assertThat(viewModel.state.value.authType).isEqualTo(AuthType.Register)

        viewModel.onAction(AuthAction.MainButtonClicked)
        advanceUntilIdle()

        viewModel.events.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(AuthEvent.CompleteSign)
        }
    }

    @Test
    fun `MainButtonClicked called, registering, failure`() = runTest {
        val error = DataError.Network.UNKNOWN
        viewModel.onAction(AuthAction.AuthTypeChanged)
        assertThat(viewModel.state.value.authType).isEqualTo(AuthType.Register)

        authRepository.error = error
        viewModel.onAction(AuthAction.MainButtonClicked)
        advanceUntilIdle()

        viewModel.events.test {
            val emission = awaitItem()
            val uiText = (emission as AuthEvent.ShowError).error
            assertThat(uiText).isEqualTo(error.asUiText())
        }
    }
}