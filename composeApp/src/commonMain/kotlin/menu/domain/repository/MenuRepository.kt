package menu.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import menu.domain.model.MenuModel

interface MenuRepository {

    suspend fun addMenu(menu: MenuModel): Flow<Resource<Unit>>
    suspend fun updateMenu(menu: MenuModel): Flow<Resource<Unit>>
    suspend fun deleteMenu(id: Long): Flow<Resource<Unit>>
    suspend fun getAllMenu(): Flow<Resource<List<MenuModel>>>
}