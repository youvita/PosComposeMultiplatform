package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class PaymentData(
    val referId: Int = Constants.PreferenceType.PAYMENT_METHOD,
    val isUsed: Boolean = false,
    val imageKHQR: ByteArray? = null,
    val bankName: String? = null,
    val accountNumber: String? = null,
    val accountName: String? = null
)
