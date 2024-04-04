package menu.data.repository

import menu.domain.model.Menu
import menu.domain.repository.MenuRepository
import org.topteam.pos.PosDatabase

class MenuRepositoryImpl(
    posDatabase: PosDatabase
): MenuRepository {

    private val db = posDatabase.appDatabaseQueries
    override suspend fun addMenu(menu: Menu) {
        db.insertMenu(id = null, name = menu.name, description = menu.description)
    }

}