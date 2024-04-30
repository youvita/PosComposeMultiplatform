package menu.domain.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

data class MenuModel @OptIn(ExperimentalResourceApi::class) constructor(
    var menuId: Long? = null,
    var image: DrawableResource? = null,
    var imageUrl: String? = null,
    var name: String? = null,
)

