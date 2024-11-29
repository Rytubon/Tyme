package com.example.thong.di

import com.example.thong.viewmodels.UserDetailViewModel
import com.example.thong.viewmodels.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
}