package com.example.tyme.di

import android.content.Context
import com.example.tyme.service.client.ApiService
import com.example.tyme.service.client.local.dao.AppDatabase
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { provideService(retrofit = get()) }
}

val databaseModule = module {
    single { provideDatabaseService(context = get()) }
}

private fun provideService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

private fun provideDatabaseService(context: Context): AppDatabase {
    return AppDatabase.getInstance(context)
}

