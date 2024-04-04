package menu.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import menu.domain.model.Menu
import menu.domain.repository.MenuRepository

class MenuViewModel(
    private val repository: MenuRepository
): ScreenModel {

    fun addMenu(menu: Menu) {
        screenModelScope.launch {
            repository.addMenu(menu)
        }
    }
}