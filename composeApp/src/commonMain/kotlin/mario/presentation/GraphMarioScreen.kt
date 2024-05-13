package mario.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.PrimaryColor
import mario.presentation.childscreen.MarioMenuScreen
import mario.presentation.component.MarioItem
import menu.domain.model.MenuModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_back
import poscomposemultiplatform.composeapp.generated.resources.img_employee
import poscomposemultiplatform.composeapp.generated.resources.img_menu
import ui.stock.presentation.AddStockScreen
import ui.stock.presentation.InventoryViewModel
import ui.stock.presentation.SearchEngineViewModel

data class GraphMarioScreen(
    val screenType: String = ScreenMario.MainScreen.route,
    val wrapContent: Boolean = false
) : Screen , KoinComponent {

    override val key = uniqueScreenKey // This key will be used to find the screen in the Navigator

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var image by remember { mutableStateOf(ImageBitmap(1,1)) }


        val marioViewModel = get<MarioViewModel>()
        val marioState = marioViewModel.state.collectAsState().value

        when(screenType){
            ScreenMario.MainScreen.route -> {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    MarioToolbar(screenType)
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
                                navigator.push(
                                    GraphMarioScreen(
                                        screenType = ScreenMario.MenuScreen.route,
                                        wrapContent = false
                                    )
                                )
                            }
                        }

                        item{
                            MarioItem(
                                name = "Employee Management",
                                image = Res.drawable.img_employee
                            ) {
                                navigator.push(
                                    GraphMarioScreen(
                                        screenType = ScreenMario.EmployeeScreen.route,
                                        wrapContent = false
                                    )
                                )
                            }
                        }

                        item{
                            MarioItem(
                                name = "Product & Stock",
                                image = Res.drawable.img_employee
                            ) {
                                navigator.push(
                                    AddStockScreen()
                                )
                            }
                        }
                    }

                }
            }

            ScreenMario.MenuScreen.route -> {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    //Toolbar
                    MarioToolbar(screenType, navigator)
                    Spacer(modifier = Modifier.height(16.dp))
                    MarioMenuScreen(
                        marioState = marioState,
                        marioEvent = marioViewModel::onEvent,
                    )
                }
            }

            ScreenMario.EmployeeScreen.route -> {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    MarioToolbar(screenType, navigator)
                    Spacer(modifier = Modifier.height(16.dp))

                    val scope = rememberCoroutineScope()

                    val singleImagePicker = rememberImagePickerLauncher(
                        selectionMode = SelectionMode.Single,
                        scope = scope,
                        onResult = { byteArrays ->
                            byteArrays.firstOrNull()?.let {
                                // Process the selected images' ByteArrays.
                                byteArrays.firstOrNull()?.let {byteArrays ->
                                    image = byteArrays.toImageBitmap()
                                }

                            }
                        }
                    )

                    Button(
                        onClick = {
                            singleImagePicker.launch()
                        }
                    ) {
                        Text("Pick Single Image")
                    }


                    Image(
                        bitmap = image,
                        contentDescription = "Selected Image",
                        modifier =
                        Modifier
                            .size(200.dp)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop,
                    )

                }
            }
        }
    }
}

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

        ScreenMario.ProductStockScreen.route -> {
            crumb = "Product & Stock"
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
