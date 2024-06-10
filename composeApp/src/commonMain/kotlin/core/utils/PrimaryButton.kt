package core.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PrimaryButton(
    text: String,
    icon: DrawableResource? = null,
    iconColor: Color = White,
    callBack: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .clip(Shapes.medium)
            .clickable {
                callBack()
            },
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(PrimaryColor),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp.takeIf { icon == null } ?: 12.dp, top = 8.dp, bottom = 8.dp)
        ) {
            icon?.let {
                Image(
                    painter = painterResource(resource = it),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    colorFilter = ColorFilter.tint(color = iconColor)
                )
//                Icon(
//                    imageVector = it,
//                    contentDescription = null,
//                    tint = White
//                )
            }
            Text(
                text = text, color = White
            )
        }
    }
}