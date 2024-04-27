package receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.theme.Styles
import core.utils.DashedDivider
import core.utils.getTextStyle

@Composable
fun BillCompanySeal() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp), color = Color.Black, thickness = 2.dp)

        Spacer(modifier = Modifier.height(5.dp))

        Column {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = "Company seal on the invoice:",
                style = getTextStyle(typography = Styles.HeaderMedium)
            )
            Spacer(modifier = Modifier.height(100.dp))
            Row {
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    HorizontalDivider(thickness = 2.dp, color = Color.Black)
                    Text(
                        text = "អ្នកទិញ\n" +
                                "Buyer",
                        style = getTextStyle(typography = Styles.HeaderMedium),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(top = 3.dp).alpha(0.5f)
                    )
                }
                Spacer(modifier = Modifier.width(13.dp))
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    HorizontalDivider(thickness = 2.dp, color = Color.Black)
                    Text(
                        text = "អ្នកលក់\n" +
                                "Seller",
                        style = getTextStyle(typography = Styles.HeaderMedium),
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(top = 3.dp).alpha(0.5f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}