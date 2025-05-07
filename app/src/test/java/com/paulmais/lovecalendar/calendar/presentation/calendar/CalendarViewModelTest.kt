package com.paulmais.lovecalendar.calendar.presentation.calendar

import com.paulmais.lovecalendar.calendar.domain.repository.FakeUserRepository
import com.paulmais.lovecalendar.calendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.calendar.domain.use_case.GenerateDaysLeft
import com.paulmais.lovecalendar.util.MainCoroutineExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class CalendarViewModelTest {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var userRepository: FakeUserRepository
    private lateinit var generateDates: GenerateDates
    private lateinit var generateDaysLeft: GenerateDaysLeft

    @BeforeEach
    fun setUp() {
        generateDates = GenerateDates()
        generateDaysLeft = GenerateDaysLeft()
        userRepository = FakeUserRepository()
        viewModel = CalendarViewModel(generateDates, userRepository, generateDaysLeft)
    }
}