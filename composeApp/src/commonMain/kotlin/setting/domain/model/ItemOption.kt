package setting.domain.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

data class ItemOption @OptIn(ExperimentalResourceApi::class) constructor(
    var type: String? = null,
    var option: String? = null,
    var price: Double? = null,
    var image: DrawableResource? = null
)
