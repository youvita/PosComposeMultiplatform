package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class SavePointData(
    val referId: Int = Constants.PreferenceType.SAVE_POINT,
    val isUsed: Boolean = false,
    val amtUsdExchange: Double? = null,
    val point: Int? = null
)
