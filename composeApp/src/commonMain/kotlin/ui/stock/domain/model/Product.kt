package ui.stock.domain.model

data class Product(
    val id: Long = 0,
    val menuId: Long?,
    val productId: Long?,
    val name: String?,
    val image: ByteArray?,
    val imageUrl: String?,
    val qty: String?,
    val price: String?,
    val discount: String?
)
