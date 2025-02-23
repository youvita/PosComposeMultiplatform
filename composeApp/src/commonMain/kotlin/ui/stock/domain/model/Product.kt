package ui.stock.domain.model

data class Product(
    val id: Long = 0,
    val menuId: Long?,
    val productId: Long?,
    val name: String?,
    val image: ByteArray?,
    val imageUrl: String?,
    val uom: String?,
    val qty: String?,
    val price: Double?,
    val amount: Double?,
    val discount: String?,
    val statusCode: String? = null,
    val statusName: String? = null,
    val isStockIn: Boolean? = false
)
