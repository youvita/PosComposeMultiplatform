package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceData(
    val referId: Int = Constants.PreferenceType.INVOICE_NO,
    val isUsed: Boolean = false,
    val dateFormat: Int? = null,
    val countingSequence: Int? = null
)
