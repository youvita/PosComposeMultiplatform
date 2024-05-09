package ui.stock.domain.model

data class Product(
    val product_id: Long = 0,
    val name: String,
    val image: String,
    val qty: Long,
    val price: Long,
    val description: String
)
