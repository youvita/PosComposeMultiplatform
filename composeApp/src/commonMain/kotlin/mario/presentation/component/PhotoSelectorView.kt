package mario.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_camera


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PhotoSelectorView(
    photoPath: ImageBitmap? = null,
    onCameraClick: () -> Unit = {},
){
    Card(
        modifier = Modifier.size(100.dp),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(0.5.dp, Color(0xFFE4E4E4)),
    ){
        Box{
            if (photoPath == null){
                Image(
                    painter = painterResource(resource = Res.drawable.ic_camera),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.medium)
                )
            }else{
                Image(
                    bitmap = photoPath,
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.medium)
                )
            }

            Card(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                shape = CircleShape,
                colors = CardDefaults.cardColors(White),
                elevation = CardDefaults.cardElevation(2.dp)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onCameraClick() },
                ){
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(resource = Res.drawable.ic_camera),
                        contentDescription = "",
                        tint = PrimaryColor
                    )
                }
            }
        }
    }
}