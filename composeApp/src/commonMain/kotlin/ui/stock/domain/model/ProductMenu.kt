package ui.stock.domain.model

data class ProductMenu(
    val id: Long = 0,
    val menuId: Long?,
    val menuName: String? = null,
    val menuImage: ByteArray? = null,
    val productId: Long?,
    val name: String?,
    val image: ByteArray?,
    val imageUrl: String?,
    val qty: String?,
    val price: String?,
    val discount: String?,
    val date: String?
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$menuId",
            "$menuName",
            "$name"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
