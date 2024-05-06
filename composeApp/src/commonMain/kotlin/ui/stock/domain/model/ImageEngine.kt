package ui.stock.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageEngine(

    @SerialName("image")
    val image: Thumbnail? = null
)
