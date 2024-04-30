package customer.domain.model

import setting.domain.model.ItemModel

data class CustomerModel(
    val customer_id: Long? = null,
    val name: String? = null,
    val phone_number: String? = null,
    var order_no: Int? = null,
    var queue_no: Int? = null,
    var table_id: Int? = null,
    var date: String? = null,
    var total_item: Int? = null,
    var total_qty: Int? = null,
    var sub_total: Double? = null,
    var total: Double? = null,
    var discount: Int? = null,
    var vat: Int? = null,
    var order_id: Long? = null,
    var item_image: String? = null,
    var item_name: String? = null,
    var item_description: String? = null,
    var item_discount: Int? = null,
    var item_price: Double? = null,
    var price: Double? = null,
    var qty: Int? = null,
    var remark: String? = null,
    var vat_in: String? = null,
    var address: String? = null,
    var point: Int? = null,
    var items: List<ItemModel>? = null
)


