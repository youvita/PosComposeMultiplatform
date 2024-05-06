package ui.stock.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(

    @SerialName("thumbnailLink")
    val thumbnailLink: String? = null
)
