package ui.stock.domain.model

data class Product(
    val product_id: Long = 0,
    val name: String,
    val image: String,
    val price: Long,
    val discount: Long,
    val category_name: String,
    val category_image: ByteArray? = null
)
