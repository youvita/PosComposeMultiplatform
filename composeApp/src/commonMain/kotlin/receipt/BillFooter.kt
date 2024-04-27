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
import core.theme.Styles
import core.utils.DashedDivider
import core.utils.getTextStyle

@Composable
fun BillFooter() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier,
            text = "VAT INCLUDED\nThank you, Please come again!\nPlease have a good day.",
            style = getTextStyle(typography = Styles.HeaderLarge),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        DashedDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp), color = Color.Black, thickness = 2.dp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier.alpha(0.5f),
            text = "handmade by love from TOP.ExD Team",
            style = getTextStyle(typography = Styles.HeaderMedium),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

    }
}