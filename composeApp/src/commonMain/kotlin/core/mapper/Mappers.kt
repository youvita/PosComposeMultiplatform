package core.mapper

import menu.domain.model.MenuModel
import org.topteam.pos.MenuEntity
import org.topteam.pos.PreferenceEntity
import org.topteam.pos.ProductEntity
import org.topteam.pos.StockEntity
import ui.settings.domain.model.PreferenceData
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductMenu
import ui.stock.domain.model.Stock

fun StockEntity.toStock(): Stock {
    return Stock(
        productId = product_id,
        stockId = stock_id,
        stockIn = stock_in,
        stockOut = stock_out,
        stockTotal = total,
        dateIn = date_in,
        dateOut = date_out,
        timeIn = time_in,
        timeOut = time_out
    )
}

fun MenuEntity.toMenu(): MenuModel {
    return MenuModel(
        menuId = id,
        name = menuName,
        image = menuImage
    )
}

fun PreferenceEntity.toPreference(): PreferenceData {
    return PreferenceData(
        preferId = prefer_id.toInt(),
        preferItem = prefer_item.toString()
    )
}
