package menu.domain.model



data class MenuModel constructor(
    var menuId: Long? = null,
    var imageRes: String? = null,
    var imageUrl: String? = null,
    var name: String? = null,
)

