package di

import core.bluetooth.BluetoothViewModel
import orderhistory.data.repository.HistoryRepositoryImpl
import orderhistory.domain.repository.OrderHistoryRepository
import orderhistory.presentation.OrderHistoryViewModel
import login.data.repository.LoginRepositoryImpl
import login.domain.repository.LoginRepository
import login.presentation.LoginViewModel
import mario.presentation.MarioViewModel
import menu.data.repository.MenuRepositoryImpl
import menu.domain.repository.MenuRepository
import menu.presentation.OrderViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.topteam.pos.PosDatabase
import platform.BluetoothDeviceFactory
import platform.DatabaseDriverFactory
import ui.stock.data.repository.InventoryRepositoryImpl
import ui.stock.data.repository.SearchEngineRepositoryImpl
import ui.stock.domain.repository.InventoryRepository
import ui.stock.domain.repository.SearchEngineRepository
import ui.stock.presentation.InventoryViewModel
import ui.stock.presentation.SearchEngineViewModel


fun appModule() = module {

    single<PosDatabase> {
        PosDatabase(
            driver = get<DatabaseDriverFactory>().createDriver()
        )
    }

    single<BluetoothViewModel> {
        BluetoothViewModel(blueFalcon = get<BluetoothDeviceFactory>().blueFalcon)
    }

    single<SearchEngineViewModel> {
        SearchEngineViewModel(repository = get())
    }

    single<LoginViewModel> {
        LoginViewModel(repository = get())
    }

    single<OrderViewModel> {
        OrderViewModel(
            repository = get(),
            repositoryInventory = get(),
            orderHistoryRepository = get()
        )
    }

    single<MarioViewModel> {
        MarioViewModel(
            repositoryMenu = get(),
            repositoryInventory = get()
        )
    }

    single<InventoryViewModel> {
        InventoryViewModel(repository = get())
    }

    single<LoginRepository> {
        LoginRepositoryImpl()
    }

    single<MenuRepository> {
        MenuRepositoryImpl(get())
    }

    single<OrderHistoryRepository> {
        HistoryRepositoryImpl(get())
    }

    single<SearchEngineRepository> {
        SearchEngineRepositoryImpl(get())
    }

    single<InventoryRepository> {
        InventoryRepositoryImpl(get())
    }

    single<OrderHistoryViewModel> {
        OrderHistoryViewModel(orderHistoryRepository = get())
    }
}

expect fun platformModule(): Module