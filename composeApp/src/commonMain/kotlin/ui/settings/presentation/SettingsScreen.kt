package ui.settings.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import core.theme.ColorD9D9D9
import core.theme.ColorDDE3F9
import core.theme.White
import core.utils.Constants
import core.utils.SharePrefer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_background
import ui.settings.components.Preference
import ui.settings.components.SettingSideBar
import ui.settings.components.SettingSideBarEvent
import ui.stock.presentation.SearchEngineViewModel

class SettingsScreen: Screen, KoinComponent {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val settingViewModel = get<SettingsViewModel>()
        var eventSideBar by rememberSaveable { mutableIntStateOf(0) }

        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorDDE3F9),
            ){
                Row(modifier = Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(resource = Res.drawable.ic_background), contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier.padding(20.dp)
                ){
                    Text(
                        text = "Settings",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Card (
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation().also { 10.dp },
                        colors = CardDefaults.cardColors(White),
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White, shape = RoundedCornerShape(8.dp))
                        ) {
                            SettingSideBar(
                                event = {
                                    when(it){
                                        is SettingSideBarEvent.PreferenceEvent ->{
                                            eventSideBar = 0
                                        }

                                        is SettingSideBarEvent.ReceiptPrinterEvent ->{
                                            eventSideBar = 1
                                        }

                                        is SettingSideBarEvent.LabelPrinterEvent ->{
                                            eventSideBar = 2
                                        }

                                        is SettingSideBarEvent.AccountEvent ->{
                                            eventSideBar = 3
                                        }

                                        is SettingSideBarEvent.PinEvent ->{
                                            eventSideBar = 4
                                        }
                                    }
                                }
                            )

                            VerticalDivider(thickness = 1.dp, color = ColorD9D9D9)

                            Box(modifier = Modifier.padding(horizontal = 20.dp)){
                                when(eventSideBar){
                                    0 ->{
                                        settingViewModel.onEvent(SettingsEvent.GetPreference())

                                        Preference(
                                            state = settingViewModel.state.value,
                                            onEvent = settingViewModel::onEvent
                                        )
                                    }

                                    1 ->{

                                    }

                                    2 ->{

                                    }

                                    3 ->{

                                    }

                                    4 ->{

                                    }
                                    else -> {}
                                }
                            }

                        }
                    }

                }
            }
        }
    }
}