package ui.stock.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class ProductStock(
    val stockId: Long? = null,
    val stockIn: Long? = null,
    val stockOut: Long? = null,
    val stockTotal: Long? = null,
    val productId: Long? = null,
    val productName: String? = null,
    val productImage: ImageBitmap? = null,
    val productImageUrl: String? = null,
    val categoryName: String? = null,
    val dateIn: String? = null,
)
