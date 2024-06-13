package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceFooterData(
    val referId: Int = Constants.PreferenceType.FOOTER,
    val isUsed: Boolean = false,
    val note: String? = null,
)
