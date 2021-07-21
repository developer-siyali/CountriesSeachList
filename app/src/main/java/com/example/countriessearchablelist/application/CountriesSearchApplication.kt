package com.example.countriessearchablelist.application

import android.app.Application
import com.example.countriessearchablelist.util.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CountriesSearchApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@CountriesSearchApplication)
            modules(appModule)
        }
    }

}