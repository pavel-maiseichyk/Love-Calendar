package com.paulmais.lovecalendar.di

import com.paulmais.lovecalendar.domain.use_case.FindFirstDayOfMonthPosition
import com.paulmais.lovecalendar.domain.use_case.FindMonthEmptyDatesAmount
import com.paulmais.lovecalendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::GenerateDates)
    singleOf(::FindFirstDayOfMonthPosition)
    singleOf(::FindMonthEmptyDatesAmount)
    viewModelOf(::HomeViewModel)
}