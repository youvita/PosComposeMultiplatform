package receipt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.app.convertToObject
import core.theme.Styles
import core.utils.Constants
import core.utils.SharePrefer
import core.utils.getTextStyle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_coffee
import ui.settings.domain.model.ShopData

@Composable
fun BillHeader(
    shop: ShopData? = null,
    isPreview: Boolean = false,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .size(90.dp)
        ) {
            shop?.shopLogo?.let {
                Image(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8)),
                    bitmap = it.toImageBitmap(),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            shop?.shopNameKhmer?.let {
                Text(
                    text = it, style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall),
                    textAlign = TextAlign.Center
                )
            }

            shop?.shopNameEnglish?.let {
                Text(
                    text = it, style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            shop?.shopTaxId?.let {
                Text(
                    text = "VAT TIN: $it", style = getTextStyle(typography = Styles.TitleMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            shop?.shopAddress?.let {
                Text(
                    text = it,
                    style = getTextStyle(typography = Styles.TitleMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                    modifier = Modifier.padding(horizontal = 5.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(5.dp))
}