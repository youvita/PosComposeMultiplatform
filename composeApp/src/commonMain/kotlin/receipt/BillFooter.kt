package receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.app.convertToObject
import core.theme.Styles
import core.utils.Constants
import core.utils.DashedDivider
import core.utils.SharePrefer
import core.utils.getTextStyle
import ui.settings.domain.model.InvoiceFooterData

@Composable
fun BillFooter(
    isPreview: Boolean = false,
    footerData: InvoiceFooterData? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        if (footerData?.isUsed == true) {

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

            Spacer(modifier = Modifier.height(5.dp))

            footerData.note?.let {
                Text(
                    modifier = Modifier,
                    text = it,
                    style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview }
                        ?: Styles.LabelSmall),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

            Spacer(modifier = Modifier.height(5.dp))

        } else {
            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

            Spacer(modifier = Modifier.height(5.dp))
        }

        Text(
            modifier = Modifier.alpha(0.5f),
            text = "handmade by love from TOP.ExD Team",
            style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

    }
}