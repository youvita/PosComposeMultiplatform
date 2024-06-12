package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateData(
    val referId: Int = Constants.ReferenceType.EXCHANGE_RATE,
    val isUsed: Boolean = false,
    val rateKHR: Double? = null
)
