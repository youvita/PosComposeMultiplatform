package menu.data.repository

import core.data.Resource
import core.data.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import menu.domain.repository.MenuRepository
import org.topteam.pos.Menu
import org.topteam.pos.PosDatabase

class MenuRepositoryImpl(
    posDatabase: PosDatabase
): MenuRepository {

    private val db = posDatabase.appDatabaseQueries
    override suspend fun addMenu(menu: Menu): Flow<Resource<Unit>> {

//        if (result == Status.SUCCESS) {
//            emit(Resource.Success(result.data))
//        }
//        if (result.status == Status.ERROR) {
//            emit(Resource.Error(result.code, result.message))
//        }
        val dataInput = db.insertMenu(name = menu.name, imageUrl = menu.imageUrl)

        return flow {
            emit(Resource.Success(dataInput))
        }
    }

    override suspend fun updateMenu(menu: Menu): Flow<Resource<Unit>> = flow {
        val dataUpdate = db.updateMenu(name = menu.name, imageUrl = menu.imageUrl, menu.id)
        emit(Resource.Success(dataUpdate))
    }

    override suspend fun deleteMenu(id: Long): Flow<Resource<Unit>> = flow {
        val dataDelete = db.deleteMenu(id)
        emit(Resource.Success(dataDelete))
    }
//        db.insertMenu(name = menu.name, imageUrl = menu.imageUrl)

    override suspend fun getAllMenu(): Flow<Resource<List<Menu>>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success(db.getAllMenu().executeAsList()))
    }


}