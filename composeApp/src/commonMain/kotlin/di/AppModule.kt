package di

import login.data.repository.LoginRepositoryImpl
import login.domain.repository.LoginRepository
import login.presentation.LoginViewModel
import org.koin.dsl.module

fun appModule() = module {

    // repositories
    single<LoginRepository> {
        LoginRepositoryImpl()
    }

    // viewmodel
    single<LoginViewModel> {
        LoginViewModel(repository = get())
    }

}