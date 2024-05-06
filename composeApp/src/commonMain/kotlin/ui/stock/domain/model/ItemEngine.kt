package ui.stock.domain.model

import kotlinx.serialization.*

@Serializable
data class ItemEngine(

    @SerialName("items")
    val items: List<ImageEngine>? = null
)
