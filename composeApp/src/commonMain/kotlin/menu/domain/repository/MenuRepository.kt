package menu.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import org.topteam.pos.Menu


interface MenuRepository {

    suspend fun addMenu(menu: Menu): Flow<Resource<Unit>>
    suspend fun updateMenu(menu: Menu): Flow<Resource<Unit>>
    suspend fun deleteMenu(id: Long): Flow<Resource<Unit>>
    suspend fun getAllMenu(): Flow<Resource<List<Menu>>>
}