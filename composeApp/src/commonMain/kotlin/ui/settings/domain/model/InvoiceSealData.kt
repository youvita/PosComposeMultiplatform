package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceSealData(
    val referId: Int = Constants.PreferenceType.INVOICE_SEAL,
    val isUsed: Boolean = false,
    val sellerSignature: ByteArray? = null,
)
