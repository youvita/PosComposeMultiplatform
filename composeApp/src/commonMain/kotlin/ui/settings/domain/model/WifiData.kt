package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class WifiData(
    val referId: Int = Constants.PreferenceType.WIFI,
    val isUsed: Boolean = false,
    val password: String? = null,
)
