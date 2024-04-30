package di

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.BluetoothDeviceFactory
import platform.DatabaseDriverFactory

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory(context = get()) }
    single { BluetoothDeviceFactory(context = get()) }
}