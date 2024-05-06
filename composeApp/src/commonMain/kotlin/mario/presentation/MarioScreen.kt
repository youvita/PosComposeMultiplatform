package mario.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import core.theme.PrimaryColor
import core.utils.LocalAppNavigator
import mario.presentation.component.MarioItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pin.presentation.PinScreen
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_back
import poscomposemultiplatform.composeapp.generated.resources.img_employee
import poscomposemultiplatform.composeapp.generated.resources.img_menu

@OptIn(ExperimentalResourceApi::class)
@Preview()
@Composable
fun MarioScreen(
    marioState: MarioState? = null,
    marioEvent: (MarioEvent) -> Unit = {},
) {

    val navigator = LocalAppNavigator.currentOrThrow

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        MarioToolbar()
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize()
        ){
            item {
                MarioItem(
                    name = "Menu Management",
                    image = Res.drawable.img_menu
                ){

                }
            }

            item{
                MarioItem(
                    name = "Employee Management",
                    image = Res.drawable.img_employee
                ) {

                }
            }
        }

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MarioToolbar(){
    var crumb by rememberSaveable { mutableStateOf("") }

    val navigator = LocalAppNavigator.currentOrThrow

    if(crumb.isEmpty()){
        Text(
            modifier = Modifier.padding(6.dp),
            text = "Super Mario (admin)",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }else{
        Row {
            Image(
                painter = painterResource(resource = Res.drawable.ic_back),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) {
                        navigator.pop()
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) {
                        navigator.pop()
                    },
                text = "Super Mario (admin)",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
            )

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = " / ",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBABABA)
                )
            )

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = crumb,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }
}