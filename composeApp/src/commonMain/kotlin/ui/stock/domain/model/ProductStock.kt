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
    val statusCode: String? = null,
    val statusName: String? = null,
    val date: String? = null,
    val time: String? = null
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
        statusCode = statusCode,
        statusName = statusName,
        date = date,
        time = time
    )
}
