package menu.data.local

import org.topteam.pos.Menu
import org.topteam.pos.PosDatabase

fun PosDatabase.insert(menu: Menu) {
    return this.appDatabaseQueries.insertMenu(menu.id, menu.name, menu.description)
}