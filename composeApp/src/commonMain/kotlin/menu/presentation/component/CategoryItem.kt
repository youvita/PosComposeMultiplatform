package menu.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.Shapes
import core.theme.White
import core.theme.textStylePrimary12Normal
import menu.domain.model.MenuModel

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: MenuModel,
    color: Color = White
) {

    Card(
        modifier = modifier.size(80.dp),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        if(category.name.equals("all",true)){
            Box(
                modifier = modifier.size(80.dp),
            ){
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    style = textStylePrimary12Normal(),
                    text = category.name?:"",
                    fontWeight = W500,
                    textAlign = TextAlign.Center
                )
            }
        }

        else{
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (category.image != null && category.image!!.isNotEmpty()){
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = RoundedCornerShape(5.dp)),
                        bitmap = category.image!!.toImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    style = textStylePrimary12Normal(),
                    fontWeight = W500,
                    text = category.name?:""
                )
            }
        }
    }
}
