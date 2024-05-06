package menu.presentation.component.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import menu.domain.model.MenuModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_coffee
import poscomposemultiplatform.composeapp.generated.resources.ic_dessert
import poscomposemultiplatform.composeapp.generated.resources.ic_empty
import poscomposemultiplatform.composeapp.generated.resources.ic_fire
import poscomposemultiplatform.composeapp.generated.resources.ic_ice
import poscomposemultiplatform.composeapp.generated.resources.ic_juice
import poscomposemultiplatform.composeapp.generated.resources.ic_snack
import setting.domain.model.ItemModel
import setting.domain.model.ItemOption


@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmptyBox(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize()){
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(resource = Res.drawable.ic_empty),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Empty",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
val fakeMenu = arrayListOf(
    MenuModel(menuId = 0L, name = "All"),
    MenuModel(menuId = 1L, name = "Coffee", imageRes = Res.drawable.ic_coffee.toString()),
    MenuModel(menuId = 2L, name = "Juice", imageRes = Res.drawable.ic_juice.toString()),
    MenuModel(menuId = 3L, name = "Snack", imageRes = Res.drawable.ic_snack.toString()),
    MenuModel(menuId = 4L, name = "Dessert", imageRes = Res.drawable.ic_dessert.toString()),
)

@OptIn(ExperimentalResourceApi::class)
val fakeItem = ItemModel(
    menuId = 1,
    image = Res.drawable.ic_coffee,
    imageUrl = "https://placehold.co/600x400.png",
    name = "Americano",
    discount = 40,
    price = 2.00,
    qty = 1,
    mood = arrayListOf(
        ItemOption(type = "mood", image = Res.drawable.ic_fire, price = 0.0),
        ItemOption(type = "mood", image = Res.drawable.ic_ice, price = 0.0),
    ),
    size = arrayListOf(
        ItemOption(type = "size", option = "S"),
        ItemOption(type = "size", option = "M"),
        ItemOption(type = "size", option = "L")
    ),
    sugar = arrayListOf(
        ItemOption(type = "sugar", option = "0%"),
        ItemOption(type = "sugar", option = "50%"),
        ItemOption(type = "sugar", option = "75%"),
        ItemOption(type = "sugar", option = "100%")
    ),
    ice = arrayListOf(
        ItemOption(type = "ice", option = "0%"),
        ItemOption(type = "ice", option = "50%"),
        ItemOption(type = "ice", option = "75%"),
        ItemOption(type = "ice", option = "100%")
    )
)

@OptIn(ExperimentalResourceApi::class)
val fakeItems: ArrayList<ItemModel> = arrayListOf(
    ItemModel(
        menuId = 1,
        image = Res.drawable.ic_coffee,
        imageUrl = "https://placehold.co/600x400.png",
        name = "Americano",
        price = 2.2,
        qty = 1,
        mood = arrayListOf(
            ItemOption(type = "mood", option = "Hot", image = Res.drawable.ic_fire, price = 0.0),
            ItemOption(type = "mood", option = "Ice", image = Res.drawable.ic_ice, price = 0.0),
        ),
        size = arrayListOf(
            ItemOption(type = "size", option = "S"),
            ItemOption(type = "size", option = "M"),
            ItemOption(type = "size", option = "L")
        ),
        sugar = arrayListOf(
            ItemOption(type = "sugar", option = "0%"),
            ItemOption(type = "sugar", option = "50%"),
            ItemOption(type = "sugar", option = "75%"),
            ItemOption(type = "sugar", option = "100%")
        ),
        ice = arrayListOf(
            ItemOption(type = "ice", option = "0%"),
            ItemOption(type = "ice", option = "50%"),
            ItemOption(type = "ice", option = "75%"),
            ItemOption(type = "ice", option = "100%")
        )
    ),

    ItemModel(
        menuId = 2,
        image = Res.drawable.ic_juice,
        imageUrl = "https://placehold.co/600x400.png",
        name = "Juice",
        price = 3.0,
        qty = 1,
        bookmark = true,
        mood = arrayListOf(
            ItemOption(option = "Ice", image = Res.drawable.ic_ice, price = 0.0),
        ),
        size = arrayListOf(
            ItemOption(type = "size", option = "S"),
            ItemOption(type = "size", option = "M"),
            ItemOption(type = "size", option = "L")
        ),
        sugar = arrayListOf(
            ItemOption(type = "sugar", option = "0%"),
            ItemOption(type = "sugar", option = "50%"),
            ItemOption(type = "sugar", option = "75%"),
            ItemOption(type = "sugar", option = "100%")
        ),
        ice = arrayListOf(
            ItemOption(type = "ice", option = "0%"),
            ItemOption(type = "ice", option = "50%"),
            ItemOption(type = "ice", option = "75%"),
            ItemOption(type = "ice", option = "100%")
        )
    ),
)