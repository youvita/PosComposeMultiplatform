package core.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: DrawableResource? = null,
    iconColor: Color = White,
    containColor: Color = PrimaryColor,
    isEnable: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .clip(Shapes.medium)
            .clickable (isEnable){
                onClick()
            }
            .alpha(1f.takeIf { isEnable }?:0.5f),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containColor),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        Row(
            modifier = modifier
                .padding(start = 10.dp, end = 10.dp.takeIf { icon == null } ?: 12.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Image(
                    painter = painterResource(resource = it),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    colorFilter = ColorFilter.tint(color = iconColor)
                )
            }
            Text(
                text = text, color = White
            )
        }
    }
}