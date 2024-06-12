package ui.settings.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class ShopData(
    val referId: Int = Constants.ReferenceType.SHOP_HEADER,
    val isUsed: Boolean = false,
    val shopLogo: ImageBitmap? = null,
    val shopNameEnglish: String? = null,
    val shopNameKhmer: String? = null,
    val shopAddress: String? = null,
    val shopTaxId: String? = null,
)
