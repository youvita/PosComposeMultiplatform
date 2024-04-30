package di

import com.russhwolf.settings.Settings
import core.bluetooth.BluetoothViewModel
import history.data.repository.HistoryRepositoryImpl
import history.domain.repository.HistoryRepository
import history.presentation.HistoryViewModel
import login.data.repository.LoginRepositoryImpl
import login.domain.repository.LoginRepository
import login.presentation.LoginViewModel
import menu.data.repository.MenuRepositoryImpl
import menu.domain.repository.MenuRepository
import menu.presentation.MenuViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.topteam.pos.PosDatabase
import platform.BluetoothDeviceFactory
import platform.DatabaseDriverFactory


fun appModule() = module {

    single<PosDatabase> {
        PosDatabase(
            driver = get<DatabaseDriverFactory>().createDriver()
        )
    }

    single<BluetoothViewModel> {
        BluetoothViewModel(blueFalcon = get<BluetoothDeviceFactory>().blueFalcon)
    }

    single<LoginViewModel> {
        LoginViewModel(repository = get())
    }

    single<MenuViewModel> {
        MenuViewModel(repository = get())
    }

    single<LoginRepository> {
        LoginRepositoryImpl()
    }

    single<MenuRepository> {
        MenuRepositoryImpl(get())
    }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get())
    }

    single<HistoryViewModel> {
        HistoryViewModel(historyRepository = get())
    }
}

expect fun platformModule(): Module