package receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.theme.Styles
import core.utils.DashedDivider
import core.utils.getTextStyle

@Composable
fun BillQueue() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp), color = Color.Black, thickness = 2.dp)

        Spacer(modifier = Modifier.height(5.dp))

        Column(horizontalAlignment = AbsoluteAlignment.Left) {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = "Wifi Password:",
                style = getTextStyle(typography = Styles.HeaderMedium),
            )
            Text(
                text = "iloveyousomuch",
                style = getTextStyle(typography = Styles.HeaderLarge)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp), color = Color.Black, thickness = 2.dp)

        Spacer(modifier = Modifier.height(5.dp))

        Column(horizontalAlignment = AbsoluteAlignment.Left) {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = "Queue Number: ",
                style = getTextStyle(typography = Styles.HeaderMedium),
            )
            Text(
                text = "99999",
                style = getTextStyle(typography = Styles.HeaderLarge)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp), color = Color.Black, thickness = 2.dp)

        Spacer(modifier = Modifier.height(5.dp))
    }
}