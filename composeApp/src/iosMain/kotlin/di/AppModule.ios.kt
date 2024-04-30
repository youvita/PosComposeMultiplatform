package di

import dev.bluefalcon.BlueFalcon
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.DatabaseDriverFactory

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
}

actual class AppModule {
    actual val blueFalcon: BlueFalcon
        get() = TODO("Not yet implemented")
}