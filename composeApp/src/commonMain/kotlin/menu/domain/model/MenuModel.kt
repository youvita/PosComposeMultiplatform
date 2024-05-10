package menu.domain.model



data class MenuModel constructor(
    var menuId: Long? = null,
    var imageRes: String? = null,
    var imageUrl: ByteArray? = null,
    var name: String? = null,
)

