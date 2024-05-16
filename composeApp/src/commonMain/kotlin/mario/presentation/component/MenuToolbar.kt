package mario.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
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
import cafe.adriel.voyager.navigator.Navigator
import core.theme.PrimaryColor
import mario.presentation.ScreenMario
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_back

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MarioToolbar(
    title: String = ScreenMario.MainScreen.route,
    navigator: Navigator? = null
){
    var crumb by rememberSaveable { mutableStateOf(title) }

    when(title){
        ScreenMario.MainScreen.route -> {
            crumb = ""
        }

        ScreenMario.MenuScreen.route -> {
            crumb = "Menu Management"
        }

        ScreenMario.EmployeeScreen.route -> {
            crumb = "Employee Management"
        }
    }

    if(crumb.isEmpty()){
        androidx.compose.material3.Text(
            modifier = Modifier.padding(6.dp),
            text = "Super Mario (admin)",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
    else{
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
                        navigator?.pop()
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
                        navigator?.pop()
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