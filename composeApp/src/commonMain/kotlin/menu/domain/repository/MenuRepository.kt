package menu.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import org.topteam.pos.Menu


interface MenuRepository {

    suspend fun addMenu(menu: Menu)
    fun getAllMenu(): Flow<Resource<List<Menu>>>
}