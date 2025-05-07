package com.paulmais.lovecalendar

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.paulmais.lovecalendar.auth.domain.FakeSessionStorage
import com.paulmais.lovecalendar.auth.domain.model.AuthInfo
import com.paulmais.lovecalendar.util.MainCoroutineExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sessionStorage: FakeSessionStorage

    @BeforeEach
    fun setUp() {
        sessionStorage = FakeSessionStorage()
        mainViewModel = MainViewModel(sessionStorage)
    }

    @Test
    fun `loading state, token exists`() = runTest {
        sessionStorage.authInfo =
            AuthInfo(accessToken = "access-token", refreshToken = "refresh-token")
        mainViewModel.state.test {
            val emission1 = awaitItem()
            assertThat(emission1).isEqualTo(MainViewModelState())

            val emission2 = awaitItem()
            assertThat(emission2.isLoading).isFalse()
            assertThat(emission2.shouldShowAuthScreen).isNotNull().isFalse()
        }
    }

    @Test
    fun `loading state, token doesn't exist`() = runTest {
        mainViewModel.state.test {
            val emission1 = awaitItem()
            assertThat(emission1).isEqualTo(MainViewModelState())

            val emission2 = awaitItem()
            assertThat(emission2.isLoading).isFalse()
            assertThat(emission2.shouldShowAuthScreen).isNotNull().isTrue()
        }
    }
}