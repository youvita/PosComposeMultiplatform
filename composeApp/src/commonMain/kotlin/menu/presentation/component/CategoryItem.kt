package menu.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import core.theme.Shapes
import core.theme.White
import core.theme.textStylePrimary12Normal
import menu.domain.model.MenuModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_dessert

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: MenuModel,
    color: Color = White
) {
    val img = if(category.name == "All") -1 else category.imageRes ?: -2
    val name = category.name?: ""
    val mod = modifier
        .height(60.dp)
        .widthIn(min = 75.dp)

    Card(
        modifier = mod,
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        if(img == -1){
            Box(
                modifier = mod
            ){
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    style = textStylePrimary12Normal(),
                    text = name,
                    textAlign = TextAlign.Center
                )
            }
        }
        else{

            ConstraintLayout(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                val (image, label) = createRefs()
                BoxWithConstraints(modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {

                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = if(img == -2) painterResource(DrawableResource(category.imageUrl?:"")) else painterResource(resource = Res.drawable.ic_dessert),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    modifier = Modifier.constrainAs(label) {
                        top.linkTo(image.bottom, margin = 8.dp)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                        bottom.linkTo(parent.bottom)
                    },
                    style = textStylePrimary12Normal(),
                    text = name
                )
            }
        }
    }
}
