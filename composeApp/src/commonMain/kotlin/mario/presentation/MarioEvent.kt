package mario.presentation

import menu.domain.model.MenuModel
import setting.domain.model.ItemModel

sealed class MarioEvent {
    data class AddItemEvent(val item: ItemModel): MarioEvent()
    data class AddMenuEvent(val menu: MenuModel): MarioEvent()
    data class UpdateMenuEvent(val menu: MenuModel): MarioEvent()
    data class UpdateItemEvent(val item: ItemModel): MarioEvent()
    data class DeleteItemEvent(val item: ItemModel): MarioEvent()
    data class DeleteMenuEvent(val menuId: Long): MarioEvent()
    data class BookmarkItemEvent(val item: ItemModel): MarioEvent()
    data class GetItemsEvent(val id: Long) : MarioEvent()
    object GetMenusEvent: MarioEvent()
    object ClearEvent: MarioEvent()
}