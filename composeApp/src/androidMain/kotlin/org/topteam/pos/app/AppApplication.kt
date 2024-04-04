package org.topteam.pos.app

import android.app.Application
import di.KoinInit
import org.koin.android.ext.koin.androidContext

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInit().init {
            androidContext(androidContext = this@AppApplication)
        }
    }
}