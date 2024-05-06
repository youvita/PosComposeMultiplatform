package mario.presentation

import core.data.Status
import menu.domain.model.MenuModel
import setting.domain.model.ItemModel

data class MarioState(
    val status: Status? = null,
    var message: String? = null,
    val menus: List<MenuModel>? = null,
    val items: List<ItemModel>? = null,
)