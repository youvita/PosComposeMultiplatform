package menu.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.ColorDDE3F9
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.RedRippleTheme
import menu.presentation.component.utils.fakeMenu
import menu.domain.model.MenuModel

@Composable
fun MenuCategoryForm(menuList: List<MenuModel> = arrayListOf(),
                     onMenuSelect: (Int) -> Unit = {}) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    Column(Modifier.fillMaxWidth()) {
        // Choose category
        Text(
            text = "Choose category",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            itemsIndexed(menuList) { index, item ->
                CompositionLocalProvider(LocalRippleTheme provides RedRippleTheme) {
                    Box(
                        modifier = Modifier
                            .clip(Shapes.medium)
                            .clickable {
                                selectedItem = index
                                onMenuSelect(selectedItem)
                            }
                            .then(
                                if (selectedItem == index) {

                                    Modifier
                                        .background(color = ColorDDE3F9, shape = Shapes.medium)
                                        .border(2.dp, color = PrimaryColor, shape = Shapes.medium)
                                } else {
                                    Modifier
                                }
                            )
                    ){
                        CategoryItem(
                            category = item,
                            color = if(selectedItem == index) ColorDDE3F9 else White
                        )
                    }
                }
            }
        }
    }
}
