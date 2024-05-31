package menu.data.repository

import core.data.Resource
import core.data.Status
import core.mapper.toMenu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import menu.domain.model.MenuModel
import menu.domain.repository.MenuRepository
import org.topteam.pos.PosDatabase

class MenuRepositoryImpl(
    posDatabase: PosDatabase
): MenuRepository {

    private val db = posDatabase.appDatabaseQueries
    override suspend fun addMenu(menu: MenuModel): Flow<Resource<Unit>> {

//        if (result == Status.SUCCESS) {
//            emit(Resource.Success(result.data))
//        }
//        if (result.status == Status.ERROR) {
//            emit(Resource.Error(result.code, result.message))
//        }
        val dataInput = db.insertMenu(menuName = menu.name, menuImage = menu.image)

        return flow {
            emit(Resource.Success(dataInput))
        }
    }

    override suspend fun updateMenu(menu: MenuModel): Flow<Resource<Unit>> = flow {
        menu.menuId?.let {
            val dataUpdate = db.updateMenu(menuName = menu.name, menuImage = menu.image, it)
            emit(Resource.Success(dataUpdate))
        }
    }

    override suspend fun deleteMenu(id: Long): Flow<Resource<Unit>> = flow {
        val dataDelete = db.deleteMenu(id)
        emit(Resource.Success(dataDelete))
    }
//        db.insertMenu(name = menu.name, imageUrl = menu.imageUrl)

    override suspend fun getAllMenu(): Flow<Resource<List<MenuModel>>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success(db.getAllMenu().executeAsList().map { it.toMenu() }))
    }


}