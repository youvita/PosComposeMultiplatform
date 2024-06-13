package receipt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.theme.Styles
import core.utils.getTextStyle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_qr_code

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BillPayment(
    isPreview: Boolean = false,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Column(
                Modifier.weight(1f), horizontalAlignment = AbsoluteAlignment.Left
            ) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "គណនីសំរាប់ទូទាត់\n" + "Payment Method :",
                    style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "ABA Bank ", style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "TEST TEST & TEST TEST", style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "000 234 234", style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall)
                )

            }

            Spacer(modifier = Modifier.width(3.dp))

            Image(
                painter = painterResource(resource = Res.drawable.ic_qr_code),
                contentDescription = "Image",
                modifier = Modifier.size(250.dp.takeIf { !isPreview } ?: 150.dp)
            )
        }
    }
}