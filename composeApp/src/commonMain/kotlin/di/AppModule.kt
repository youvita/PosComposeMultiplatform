package di
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
import ui.bluetooth.presentation.BluetoothDeviceViewModel
import ui.parking.data.repository.ParkingRepositoryImpl
import ui.parking.domain.repository.ParkingRepository
import ui.parking.presentation.ParkingViewModel
import ui.settings.data.SettingRepositoryImpl
import ui.settings.domain.repository.SettingRepository
import ui.settings.presentation.SettingsViewModel
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

    single<BluetoothDeviceViewModel> {
        BluetoothDeviceViewModel(blueFalcon = get<BluetoothDeviceFactory>().blueFalcon)
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

    single<SettingsViewModel> {
        SettingsViewModel(repository = get())
    }

    single<ParkingViewModel> {
        ParkingViewModel(repository = get())
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

    single<SettingRepository> {
        SettingRepositoryImpl(get())
    }

    single<ParkingRepository> {
        ParkingRepositoryImpl(get())
    }

    single<OrderHistoryViewModel> {
        OrderHistoryViewModel(orderHistoryRepository = get())
    }
}

expect fun platformModule(): Module