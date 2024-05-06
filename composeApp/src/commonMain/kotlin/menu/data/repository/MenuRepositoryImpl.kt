package menu.data.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import menu.domain.repository.MenuRepository
import org.topteam.pos.Menu
import org.topteam.pos.PosDatabase

class MenuRepositoryImpl(
    posDatabase: PosDatabase
): MenuRepository {

    private val db = posDatabase.appDatabaseQueries
    override suspend fun addMenu(menu: Menu) {
        db.insertMenu(name = menu.name, imageUrl = menu.imageUrl)
    }

    override fun getAllMenu(): Flow<Resource<List<Menu>>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success(db.getAllMenu().executeAsList()))
    }


}