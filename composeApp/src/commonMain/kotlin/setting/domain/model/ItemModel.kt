package setting.domain.model

import org.jetbrains.compose.resources.ExperimentalResourceApi
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_dessert

data class ItemModel constructor(
    var itemId: Long? = null,
    var menuId: Long? = null,
    var product_id: Long? = null,
    var image_product: ByteArray? = null,
    var imageUrl: String? = null,
    var itemCode: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var remark: String? = null,
    var discount: Int? = null,
    var price: Double? = null,
    var uom: String? = null,
    var amount: Double? = null,
    var qty: Int? = null,
    var qtySelected: Int? = 1,
    var bookmark: Boolean? = false,
    var mood: List<ItemOption>? = null,
    var size: List<ItemOption>? = null,
    var sugar: List<ItemOption>? = null,
    var ice: List<ItemOption>? = null,
)

fun ItemModel.doesMatchSearchQuery(query: String): Boolean {
    val matchingCombinations = listOf(
        "$menuId",
        "$product_id",
        "$name"
    )
    return matchingCombinations.any {
        it.contains(query, ignoreCase = true)
    }
}

@OptIn(ExperimentalResourceApi::class)
fun ItemModel.mapItem(): Item {
    return Item(
        menu_id = menuId,
        image = Res.drawable.ic_dessert,
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
        image = Res.drawable.ic_dessert,
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
