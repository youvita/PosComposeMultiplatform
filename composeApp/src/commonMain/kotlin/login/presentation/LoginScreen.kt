package login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.Status
import core.theme.Black
import core.theme.PrimaryColor
import core.utils.LocalAppNavigator
import main.MainScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_background
import poscomposemultiplatform.composeapp.generated.resources.ic_visibility
import poscomposemultiplatform.composeapp.generated.resources.ic_visibility_off
import poscomposemultiplatform.composeapp.generated.resources.login
import poscomposemultiplatform.composeapp.generated.resources.login_now
import poscomposemultiplatform.composeapp.generated.resources.logo_top
import poscomposemultiplatform.composeapp.generated.resources.password
import poscomposemultiplatform.composeapp.generated.resources.username
import poscomposemultiplatform.composeapp.generated.resources.welcome
import poscomposemultiplatform.composeapp.generated.resources.welcome_back

class LoginScreen: Screen, KoinComponent {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val loginModel = get<LoginViewModel>()
        val state = loginModel.uiState.collectAsState().value
        val navigator = LocalAppNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        var passwordVisibility by remember {
            mutableStateOf(false)
        }

        var isLoginSuccess by remember {
            mutableStateOf(false)
        }

        val loading by rememberUpdatedState(state.isLoading)
        LaunchedEffect(loading) {
            if (state.status == Status.SUCCESS) {
                isLoginSuccess = true
            }
        }

        if (isLoginSuccess) {
            isLoginSuccess = false
            navigator.push(MainScreen())
        }

        Scaffold { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                //Welcome banner left
                Box(
                    modifier = Modifier
                        .weight(1f)
//                        .background(ColorDDE3F9)
                        .fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(resource = Res.drawable.ic_background),
                        contentDescription = ""
                    )
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(50.dp),
                        contentScale = ContentScale.Fit,
                        painter = painterResource(Res.drawable.welcome),
                        contentDescription = ""
                    )
                }

                //Login container
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable (
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ){
                            focusManager.clearFocus()
                        }
                ) {
                    Button(
                        onClick = {
//                            navController.navigate(Screen.PinScreen.route)
                            loginModel.onLoginClick()
                        }
                    ) {
                        Text(text = "Pin")
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
//                    Button(
//                        onClick = {
//                            navController.navigate(Screen.MainScreen.route) {
//                                popUpTo(Screen.LoginScreen.route) {
//                                    inclusive = true
//                                }
//                            }
//                        }
//                    ) {
//                        Text(text = "Login")
//                    }

                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(bottom = 20.dp),
                            painter = painterResource(Res.drawable.logo_top),
                            contentDescription = ""
                        )

                        Text(
                            text = stringResource(Res.string.login).uppercase(),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )

                        Text(
                            text = stringResource(Res.string.welcome_back),
                            fontSize = 16.sp,
                        )

                        //Input username
                        TextField(
                            value = state.username,
                            onValueChange = { loginModel.onUsernameChange(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .focusRequester(remember { FocusRequester() }),
                            shape = RoundedCornerShape(10.dp),
                            placeholder = {Text(stringResource(Res.string.username))},
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription = "Username",
                                    tint = PrimaryColor)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )

                        //Input password
                        TextField(
                            value = state.password,
                            onValueChange = { loginModel.onPasswordChange(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .focusRequester(remember { FocusRequester() }),
                            shape = RoundedCornerShape(10.dp),
                            placeholder = {Text(stringResource(Res.string.password))},
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Lock,
                                    contentDescription = "Password",
                                    tint = PrimaryColor)
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { passwordVisibility = !passwordVisibility }
                                ) {
                                    Image(
                                        painter = painterResource(if (passwordVisibility) Res.drawable.ic_visibility else Res.drawable.ic_visibility_off),
                                        contentDescription = null
                                    )
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )

                        //Login Button
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 24.dp)
                                .height(50.dp),
//                            elevation = 0.dp,
//                            cornersInDp = 10.dp,
//                            onClickAction = {
////                                viewModel.onLoginClick(uiState.username,uiState.password)
//                            }
                        ) {

                            Text(text = stringResource(Res.string.login_now))
//                            if (uiState.showDialog) {
//                                CircularProgressIndicator(
//                                    color = MaterialTheme.colorScheme.onPrimary,
//                                    modifier = Modifier.size(24.dp)
//                                )
//                            } else {
//                                Text(
//                                    text = "Login now",
//                                    fontSize = 16.sp
//                                )
//                            }
                        }

                    }
                }
            }
        }
    }

}