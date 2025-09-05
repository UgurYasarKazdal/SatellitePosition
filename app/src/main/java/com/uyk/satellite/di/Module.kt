package com.uyk.satellite.di

import com.uyk.satellite.list.domain.SatelliteRepository
import com.uyk.satellite.list.presentation.SatelliteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val myModules = module {
    single { SatelliteRepository(androidContext()) }

    viewModel { SatelliteViewModel(get()) }
}