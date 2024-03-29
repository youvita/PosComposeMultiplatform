package org.topteam.pos.app

import android.app.Application
import di.KoinInit
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.topteam.pos.di.androidModule

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInit().init {
//            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@AppApplication)
            modules(
                listOf(
                    androidModule,
                ),
            )
        }
    }
}