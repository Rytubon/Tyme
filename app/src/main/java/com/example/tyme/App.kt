package com.example.tyme

import android.app.Application
import com.example.tyme.di.databaseModule
import com.example.tyme.di.networkModule
import com.example.tyme.di.repositoryModule
import com.example.tyme.di.serviceModule
import com.example.tyme.di.viewModelModules
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