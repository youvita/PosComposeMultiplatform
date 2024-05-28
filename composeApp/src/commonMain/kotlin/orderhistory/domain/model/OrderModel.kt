package orderhistory.domain.model

data class OrderModel(
    var order_id: Long? = null,
    var customer_id: Long? = null,
    var order_no: Long? = null,
    var queue_no: Long? = null,
    var date: String? = null,
    var total_item: Long? = null,
    var total_qty: Long? = null,
    var sub_total: Double? = null,
    var discount: Long? = null,
    var vat: Long? = null,
    var total: Double? = null,
    var status: String? = null
)