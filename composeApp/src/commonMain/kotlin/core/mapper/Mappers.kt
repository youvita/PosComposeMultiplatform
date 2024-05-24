package core.mapper

import menu.domain.model.MenuModel
import org.topteam.pos.MenuEntity
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

fun MenuEntity.toMenu(): MenuModel {
    return MenuModel(
        menuId = id,
        name = name,
        image = imageUrl
    )
}
