package ui.bluetooth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.DarkGreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_wifi
import ui.bluetooth.presentation.UiEvent

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FoundDeviceCard(
    deviceName: String?,
    macId: String,
    onClick: (String) -> Unit
) {
    Box(modifier = Modifier.clickable {
        onClick(macId)
    }) {
        Row (
            modifier = Modifier
                .padding(start = 50.dp, top = 10.dp, bottom = 10.dp, end = 25.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = deviceName.orEmpty(),
                        fontSize = 12.sp
                    )
                }

                Icon(
                    painter = painterResource(resource = Res.drawable.ic_wifi),
                    contentDescription = null,
                    tint = DarkGreen
                )
            }
        }
    }

}