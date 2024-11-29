package com.example.thong.di

import com.example.thong.service.repositories.UserRepository
import com.example.thong.service.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
}