package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class ShopData(
    val referId: Int = Constants.PreferenceType.SHOP_HEADER,
    val isUsed: Boolean = false,
    val shopLogo: ByteArray? = null,
    val shopNameEnglish: String? = null,
    val shopNameKhmer: String? = null,
    val shopAddress: String? = null,
    val shopTaxId: String? = null,
)
