package ui.stock.domain.model

data class Product(
    val menu_id: Long?,
    val product_id: Long?,
    val name: String?,
    val image: ByteArray?,
    val imageUrl: String?,
    val qty: Long?,
    val price: Long?,
    val discount: Long?,
    val category_name: String?,
    val category_image: ByteArray? = null
)
