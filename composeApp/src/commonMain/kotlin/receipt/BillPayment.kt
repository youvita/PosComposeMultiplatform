package receipt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.Styles
import core.utils.DashedDivider
import core.utils.getTextStyle
import ui.settings.domain.model.PaymentData

@Composable
fun BillPayment(
    isPreview: Boolean = false,
    payment: PaymentData? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (payment?.isUsed == true) {

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f), horizontalAlignment = AbsoluteAlignment.Left
                ) {
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = "គណនីសំរាប់ទូទាត់\n" + "Payment Method :",
                        style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    payment.bankName?.let {
                        Text(
                            text = it, style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    payment.accountName?.let {
                        Text(
                            text = it, style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    payment.accountNumber?.let {
                        Text(
                            text = it, style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall)
                        )
                    }

                }

                Spacer(modifier = Modifier.width(3.dp))

                payment.imageKHQR?.let {
                    if (it.isNotEmpty()) {
                        Image(
                            modifier = Modifier.size(250.dp.takeIf { !isPreview } ?: 150.dp),
                            bitmap = it.toImageBitmap(),
                            contentDescription = "Image",
                        )
                    }
                }

            }
        }
    }
}