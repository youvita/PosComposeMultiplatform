package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class VatData(
    val referId: Int = Constants.PreferenceType.VAT,
    val isUsed: Boolean = false,
    val taxValue: Int? = null,
)
