package orderhistory.domain.model

data class TransactionHistory(
    val billNo: String? = null,
    val date: String? = null,
    val orderBy: String? = null,
    val discount: String? = null,
    val total: String? = null,
    val status: String? = null,
    val orderId: Long? = null
)
