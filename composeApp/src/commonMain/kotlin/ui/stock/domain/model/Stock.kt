package ui.stock.domain.model

data class Stock(
    val stock_id: Long = 0,
    val product_id: Long,
    val stock_in: Long,
    val stock_out: Long,
    val box: Long,
    val total: Long,
    val date_in: String,
    val date_out: String,
)
