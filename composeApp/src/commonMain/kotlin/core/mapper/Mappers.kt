package core.mapper

import org.topteam.pos.ProductEntity
import org.topteam.pos.StockEntity
import ui.stock.domain.model.Product
import ui.stock.domain.model.Stock

fun StockEntity.toStock(): Stock {
    return Stock(
        productId = product_id,
        stockId = stock_id,
        stockIn = stock_in,
        stockOut = stock_out,
        stockTotal = total,
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        menuId = menu_id,
        productId = product_id,
        name = name,
        image = image,
        imageUrl = imageUrl,
        qty = qty.toString(),
        price = price.toString(),
        discount = discount.toString()
    )
}
