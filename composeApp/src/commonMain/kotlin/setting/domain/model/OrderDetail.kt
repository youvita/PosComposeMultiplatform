package setting.domain.model

data class OrderDetail(

    var order_detail_id: Long = 0,
    var order_id: Long? = null,
    var item_image: String? = null,
    var item_name: String? = null,
    var item_description: String? = null,
    var item_discount: Int? = null,
    var item_price: Double? = null,
    var item_qty: Int? = null,
    var item_option: List<ItemOption>? = null
)

fun List<OrderDetail>.mapToItemModel(): List<ItemModel> {
    val items = arrayListOf<ItemModel>()
    this.forEach {
//        val options = sanitizeItemOption(it.item_option.toString())
        items.add(
            ItemModel(
                name = it.item_name,
                description = it.item_description,
                imageUrl = it.item_image,
                qty = it.item_qty,
                discount = it.item_discount,
                price = it.item_price,
//                mood = options.filter { option -> option.type.equals("mood", true) },
//                size = options.filter { option -> option.type.equals("size", true) },
//                sugar = options.filter { option -> option.type.equals("sugar", true) },
//                ice = options.filter { option -> option.type.equals("ice", true) }
            )
        )
    }
    return items
}

//private fun sanitizeItemOption(itemOptions: String): List<ItemOption>{
//    val options = arrayListOf<ItemOption>()
//    options.addAll(fromJson<List<ItemOption>>(itemOptions))
//    return options
//}