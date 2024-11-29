package com.example.thong

import android.app.Application
import com.example.thong.di.databaseModule
import com.example.thong.di.networkModule
import com.example.thong.di.repositoryModule
import com.example.thong.di.serviceModule
import com.example.thong.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModules,
                    serviceModule,
                    networkModule,
                    repositoryModule,
                    databaseModule
                )
            )
        }
    }
}