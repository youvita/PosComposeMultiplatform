package app

import android.app.Application
import dev.icerock.moko.mvvm.compose.BuildConfig
import di.KoinInit
import di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInit().init {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@AppApplication)
            modules(
                listOf(
                    androidModule,
                ),
            )
        }
    }
}