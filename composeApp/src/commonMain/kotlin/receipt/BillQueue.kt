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
import core.app.convertToObject
import core.theme.Styles
import core.utils.Constants
import core.utils.DashedDivider
import core.utils.SharePrefer
import core.utils.getTextStyle
import ui.settings.domain.model.QueueData
import ui.settings.domain.model.WifiData

@Composable
fun BillQueue(
    isPreview: Boolean = false,
) {
    val queueNumber = SharePrefer.getPrefer("${Constants.PreferenceType.QUEUE}")
    val queue = convertToObject<QueueData>(queueNumber)

    val wifiPassword = SharePrefer.getPrefer("${Constants.PreferenceType.WIFI}")
    val wifi = convertToObject<WifiData>(wifiPassword)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        if (wifi.isUsed) {
            DashedDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (isPreview) {
                            Modifier.padding(horizontal = 1.dp)
                        } else {
                            Modifier.padding(horizontal = 2.dp)
                        }
                    ), color = Color.Black, thickness = 2.dp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Column(horizontalAlignment = AbsoluteAlignment.Left) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "Wifi Password:",
                    style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview }
                        ?: Styles.LabelSmall),
                )
                Text(
                    text = "iloveyousomuch",
                    style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview }
                        ?: Styles.LabelSmall)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isPreview) {
                        Modifier.padding(horizontal = 1.dp)
                    } else {
                        Modifier.padding(horizontal = 2.dp)
                    }
                ), color = Color.Black, thickness = 2.dp)
        }

        if (queue.isUsed) {
            Spacer(modifier = Modifier.height(5.dp))

            Column(horizontalAlignment = AbsoluteAlignment.Left) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "Queue Number: ",
                    style = getTextStyle(typography = Styles.HeaderMedium.takeIf { !isPreview }
                        ?: Styles.LabelSmall),
                )
                Text(
                    text = "99999",
                    style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview }
                        ?: Styles.LabelSmall)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isPreview) {
                        Modifier.padding(horizontal = 1.dp)
                    } else {
                        Modifier.padding(horizontal = 2.dp)
                    }
                ), color = Color.Black, thickness = 2.dp)

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}