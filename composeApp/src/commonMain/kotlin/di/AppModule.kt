package di

import login.data.repository.LoginRepositoryImpl
import login.domain.repository.LoginRepository
import login.presentation.LoginViewModel
import menu.data.repository.MenuRepositoryImpl
import menu.domain.repository.MenuRepository
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
        LoginViewModel(
            repository = get(),
            menuRepository = get()
        )
    }

    single<LoginRepository> {
        LoginRepositoryImpl()
    }

    single<MenuRepository> {
        MenuRepositoryImpl(get())
    }
}

expect fun platformModule(): Module