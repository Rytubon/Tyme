package com.example.tyme.di

import com.example.tyme.viewmodels.UserDetailViewModel
import com.example.tyme.viewmodels.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
}