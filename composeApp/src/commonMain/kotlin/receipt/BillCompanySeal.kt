package receipt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.Styles
import core.utils.DashedDivider
import core.utils.getTextStyle
import ui.settings.domain.model.InvoiceSealData

@Composable
fun BillCompanySeal(
    isPreview: Boolean = false,
    invoiceSeal: InvoiceSealData? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (invoiceSeal?.isUsed == true) {

            Spacer(modifier = Modifier.height(5.dp))

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = "Company seal on the invoice:",
                        style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview } ?: Styles.LabelSmall)
                    )

                    Row(
                        modifier = Modifier.fillMaxSize().height(150.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HorizontalDivider(thickness = 2.dp.takeIf { !isPreview } ?: 1.dp, color = Color.Black)
                            Text(
                                text = "អ្នកទិញ\n" +
                                        "Buyer",
                                style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(top = 3.dp).alpha(0.5f)
                            )
                        }

                        Spacer(modifier = Modifier.width(13.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            invoiceSeal.sellerSignature?.let {
                                if (it.isNotEmpty()) {
                                    Image(
                                        modifier = Modifier.size(70.dp),
                                        bitmap = it.toImageBitmap(),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            HorizontalDivider(thickness = 2.dp.takeIf { !isPreview } ?: 1.dp, color = Color.Black)

                            Text(
                                text = "អ្នកលក់\n" +
                                        "Seller",
                                style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                                textAlign = TextAlign.End,
                                modifier = Modifier.padding(top = 3.dp).alpha(0.5f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}