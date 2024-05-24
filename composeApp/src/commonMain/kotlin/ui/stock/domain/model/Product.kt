package ui.stock.domain.model

data class Product(
    val menuId: Long?,
    val productId: Long?,
    val name: String?,
    val image: ByteArray?,
    val imageUrl: String?,
    val qty: String?,
    val price: String?,
    val discount: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Product

        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        return image?.contentHashCode() ?: 0
    }
}
