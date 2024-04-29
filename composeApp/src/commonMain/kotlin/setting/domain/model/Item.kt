package setting.domain.model

data class Item(
    var item_id: Long = 0,
    var menu_id: Long? = null,
    var image: Int? = null,
    var image_url: String? = null,
    var name: String? = null,
    var description: String? = null,
    var remark: String? = null,
    var discount: Int? = null,
    var price: Double? = null,
    var qty: Int? = null,
    var bookmark: Int? = 0,
    var mood: List<ItemOption>? = null,
    var size: List<ItemOption>? = null,
    var sugar: List<ItemOption>? = null,
    var ice: List<ItemOption>? = null,

)

fun Item.mapItem(): ItemModel {
    return ItemModel(
        itemId = item_id,
        menuId = menu_id,
        image = image,
        imageUrl = image_url,
        qty = qty,
        bookmark = bookmark == 1,
        name = name,
        description = description,
        remark = remark,
        discount = discount,
        price = price,
        mood = mood,
        size = size,
        sugar = sugar,
        ice = ice
    )
}