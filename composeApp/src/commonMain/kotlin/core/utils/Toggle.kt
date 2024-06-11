package core.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.theme.ColorA3A3A3
import core.theme.ColorE4E4E4
import core.theme.PrimaryColor
import core.theme.White
import core.theme.fontSizeContent


@Composable
fun Toggle(
    text: String = "Label",
    enabled: Boolean = true,
    checked: Boolean = false,
    fontWeight: FontWeight = FontWeight.Bold,
    onCheckedChange: (Boolean) -> Unit = {}
){
    
    Row(Modifier.height(30.dp)) {
        Switch(
            enabled = enabled,
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            },
            thumbContent = {

            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = PrimaryColor,
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = ColorE4E4E4,
                uncheckedTrackColor = ColorA3A3A3,
                uncheckedBorderColor = ColorA3A3A3,
                uncheckedIconColor = ColorA3A3A3,
                disabledUncheckedTrackColor = White,
                disabledCheckedTrackColor = Color(0xFFA3A3A3),
                disabledCheckedThumbColor = Color(0xFFEAEAEA),
            ),
            modifier = Modifier
                .scale(0.5f)
                .padding(0.dp)
                .offset(x = ((-25).dp))
                .align(Alignment.CenterVertically),
        )

        Text(
            modifier = Modifier
                .padding(0.dp)
                .offset(x = ((-15).dp))
                .align(Alignment.CenterVertically),
            text = text,
            style = TextStyle(
                fontSize = fontSizeContent,
                fontWeight = fontWeight
            )
        )
    }
}