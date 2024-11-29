package com.example.tyme.di

import com.example.tyme.service.repositories.UserRepository
import com.example.tyme.service.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
}