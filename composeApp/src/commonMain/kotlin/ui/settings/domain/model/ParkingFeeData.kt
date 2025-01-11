package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class ParkingFeeData(
    val referId: Int = Constants.PreferenceType.PARKING_FEE,
    val isUsed: Boolean = false,
    val fee: Double? = null
)
