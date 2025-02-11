package ui.stock.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StockItem(
    val stockId: Long? = null,
    val stockIn: Long? = null,
    val stockOut: Long? = null,
    val stockTotal: Long? = null,
    val productId: Long? = null,
    val productName: String? = null,
    val productPrice: String? = null,
    val categoryName: String? = null,
    val status: String? = null,
    val date: String? = null,
    val time: String? = null,
)
