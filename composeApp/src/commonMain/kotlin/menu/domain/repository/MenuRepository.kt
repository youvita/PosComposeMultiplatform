package menu.domain.repository

import menu.domain.model.Menu

interface MenuRepository {

    suspend fun addMenu(menu: Menu)
}