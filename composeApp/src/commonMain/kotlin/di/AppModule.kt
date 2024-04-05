package di

import login.data.repository.LoginRepositoryImpl
import login.domain.repository.LoginRepository
import login.presentation.LoginViewModel
import menu.data.repository.MenuRepositoryImpl
import menu.domain.repository.MenuRepository
import menu.presentation.MenuViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.topteam.pos.PosDatabase
import platform.DatabaseDriverFactory


fun appModule() = module {

    single<PosDatabase> {
        PosDatabase(
            driver = get<DatabaseDriverFactory>().createDriver()
        )
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
}

expect fun platformModule(): Module