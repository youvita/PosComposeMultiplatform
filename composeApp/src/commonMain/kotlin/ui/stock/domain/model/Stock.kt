package ui.stock.domain.model

data class Stock(
    val stockId: Long? = null,
    val productId: Long? = null,
    val stockIn: Long? = null,
    val stockOut: Long? = null,
    val stockBox: Long? = null,
    val stockTotal: Long? = null,
    val dateIn: String? = null,
    val dateOut: String? = null,
    val timeIn: String? = null,
    val timeOut: String? = null,
)
