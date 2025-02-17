package ui.stock.domain.model

data class Stock(
    val stockId: Long? = null,
    val productId: Long? = null,
    val stockIn: Long? = null,
    val stockOut: Long? = null,
    val stockBox: Long? = null,
    val stockTotal: Long? = null,
    val unitPrice: Long? = null,
    val statusCode: String? = null,
    val statusName: String? = null,
    val date: String? = null,
    val time: String? = null,
)
