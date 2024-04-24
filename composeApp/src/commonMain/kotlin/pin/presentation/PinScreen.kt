package pin.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.theme.Black
import core.theme.ColorDDE3F9
import core.utils.LocalAppNavigator
import login.presentation.LoginScreen
import main.MainScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import pin.utils.PinLock
//import pin.utils.PinManagerViewModel
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_back
import poscomposemultiplatform.composeapp.generated.resources.ic_background
import poscomposemultiplatform.composeapp.generated.resources.ic_exit
import poscomposemultiplatform.composeapp.generated.resources.pin_image
import poscomposemultiplatform.composeapp.generated.resources.welcome

class PinScreen: Screen, KoinComponent {


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalAppNavigator.currentOrThrow

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //if first login
            if (false){
                Row {
                    Box(modifier = Modifier
                        .weight(1f)
                        .background(ColorDDE3F9)
                        .fillMaxSize()){
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                            painter = painterResource(resource = Res.drawable.ic_background),
                            contentDescription = "")
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(50.dp),
                            contentScale = ContentScale.Fit,
                            painter = painterResource(resource = Res.drawable.welcome),
                            contentDescription = "")
                    }

                    Box(modifier = Modifier.weight(1f)){

                        PinContent(navigator)

                        IconButton(
                            modifier = Modifier.padding(10.dp),
                            onClick = {
                                navigator.push(LoginScreen())
                            }
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_back),
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
            //If already login
            else{
                Box(
                    modifier = Modifier
                        .background(ColorDDE3F9)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(Res.drawable.ic_background),
                        contentDescription = "")

                    Card (
                        shape = RoundedCornerShape(40.dp),
                        elevation = CardDefaults.elevatedCardElevation().also { 10.dp }
                    ){
                        PinContent(navigator)
                    }

                    IconButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopEnd),
                        onClick = {
                            navigator.pop()
                        }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_exit),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PinContent(navigator: Navigator) {
    PinLock(
        title = { pinExists ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.pin_image),
                    contentDescription = "Pin"
                )

                Text(
                    text = if (pinExists) "Welcome Back Cashier TIGER" else "Please Create pin",
                    color = Black,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Enter 6 numbers to keep your account safe",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        },
        color = Color.White,
        onPinCorrect = {
            // pin is correct, navigate or hide pin lock
            println(">>>>: Pin is correct")
            navigator.push(MainScreen())
        },
        onPinIncorrect = {
            // pin is incorrect, show error
            println(">>>>: Pin is incorrect")
        },
        onPinCreated = {
            // pin created for the first time, navigate or hide pin lock
            println(">>>>: Pin is created for the first time")
            navigator.push(MainScreen())
        }
    )
}
