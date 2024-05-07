package core.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    image: Any,
) {
    KamelImage(modifier = modifier,
        resource = asyncPainterResource(data = image),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

//    Image(
//        painter = image,
//        contentDescription = null
//    )
}