package setting.domain.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

data class ItemModel @OptIn(ExperimentalResourceApi::class) constructor(
    var itemId: Long? = null,
    var menuId: Long? = null,
    var image: DrawableResource? = null,
    var imageUrl: String? = null,
    var name: String? = null,
    var description: String? = null,
    var remark: String? = null,
    var discount: Int? = null,
    var price: Double? = null,
    var qty: Int? = null,
    var bookmark: Boolean? = false,
    var mood: List<ItemOption>? = null,
    var size: List<ItemOption>? = null,
    var sugar: List<ItemOption>? = null,
    var ice: List<ItemOption>? = null,
)

@OptIn(ExperimentalResourceApi::class)
fun ItemModel.mapItem(): Item {
    return Item(
        menu_id = menuId,
        image = image,
        image_url = imageUrl,
        name = name,
        description = description,
        remark = remark,
        discount = discount,
        price = price,
        qty = qty,
        bookmark = if(bookmark == true) 1 else 0,
        mood = mood,
        size = size,
        sugar = sugar,
        ice = ice
    )
}

@OptIn(ExperimentalResourceApi::class)
fun ItemModel.mapUpdateItem(): Item {
    return Item(
        item_id = itemId?: -1,
        menu_id = menuId,
        image = image,
        image_url = imageUrl,
        name = name,
        description = description,
        remark = remark,
        discount = discount,
        price = price,
        qty = qty,
        bookmark = if(bookmark == true) 1 else 0,
        mood = mood,
        size = size,
        sugar = sugar,
        ice = ice
    )
}

fun ItemModel.mapToOrderDetail(orderId: Long): OrderDetail {
    return OrderDetail(
        order_id = orderId,
        item_image = imageUrl,
        item_name = name,
        item_description = description,
        item_discount = discount,
        item_price = price,
        item_qty = qty
    )
}
fun List<ItemModel>.mapToOrderDetails(orderId: Long): List<OrderDetail> {
    val orderDetails: ArrayList<OrderDetail> = arrayListOf()
    this.forEach {
        val options = arrayListOf<ItemOption>()
        it.mood?.let { it1 -> options.addAll(it1) }
        it.size?.let { it1 -> options.addAll(it1) }
        it.sugar?.let { it1 -> options.addAll(it1) }
        it.ice?.let { it1 -> options.addAll(it1) }

        orderDetails.add(
            OrderDetail(
                order_id = orderId,
                item_image = it.imageUrl,
                item_name = it.name,
                item_description = it.description,
                item_discount = it.discount,
                item_price = it.price,
                item_option = options,
                item_qty = it.qty
            )
        )
    }
    return orderDetails
}
