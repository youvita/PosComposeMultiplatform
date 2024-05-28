package menu.domain.model


data class BillModel(
    val id: Long? = null,
    val totalAmount: Double? = 0.0,
    val subTotal: Double? = 0.0,
    val discountAmount: Double? = 0.0,
    val discount: Int? = 0,
    val totalItem: Int? = 0,
    val totalQty: Int? = 0,
    val vat: Int? = 0,
    val vatAmount: Double? = 0.0,
    val queue: Int? = 0,
    val billNo: String? = "0",
    val date: String? = "",
)
