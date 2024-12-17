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
    val productPrice: String? = null,
    val categoryName: String? = null,
    val dateIn: String? = null,
    val timeIn: String? = null,
    val dateOut: String? = null,
    val timeOut: String? = null
)

fun ProductStock.toStockItem(): StockItem {
    return StockItem(
        stockId = stockIn,
        stockIn = stockIn,
        stockOut = stockOut,
        stockTotal = stockTotal,
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        categoryName = categoryName,
        dateIn = dateIn,
        timeIn = timeIn,
        dateOut = dateOut,
        timeOut = timeOut
    )
}
