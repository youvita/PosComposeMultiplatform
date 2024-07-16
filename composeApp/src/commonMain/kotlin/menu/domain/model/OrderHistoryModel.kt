package menu.domain.model

data class OrderHistoryModel(
    val id: Long,
    val order_no: String?,
    val queue_no: String?,
    val date: String?,
    val total_item: String?,
    val total_qty: String?,
    val sub_total: String?,
    val discount: String?,
    val vat: String?,
    val total: String?,
    val status: String?,
)

fun OrderHistoryModel.doesMatchSearchQuery(query: String): Boolean {
    val matchingCombinations = listOf(
        "$order_no",
        "$total",
        "$date"
    )
    return matchingCombinations.any {
        it.contains(query, ignoreCase = true)
    }
}

