package core.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_exit


@Composable
fun DialogLoading(
    onDismissRequest: () -> Unit = {}
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp)
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(
                    color = PrimaryColor
                )
            }
        }
    }
}

@Composable
fun DialogSuccess(
    onDismissRequest: () -> Unit = {}
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp)
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "Success",
                    tint = PrimaryColor,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview()
@Composable
fun DialogError(
    title: String? = null,
    message: String? = null,
    onClose: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ){
        Column(
            modifier = Modifier
                .padding(vertical = 30.dp, horizontal = 20.dp)
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)

        ){
            Row{
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    text = title?: "Error",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_exit),
                        contentDescription = ""
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = message?: "Unknown",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { onClose() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = PrimaryColor,
                    contentColor = White
                ),
                border = null,
                shape = Shapes.medium,
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            ){
                Text(
                    text = "Close",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview()
@Composable
fun DialogAlert(
    title: String? = null,
    buttonText: String? = null,
    message: String? = null,
    onButtonClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ){
        Column(
            modifier = Modifier
                .padding(vertical = 30.dp, horizontal = 20.dp)
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)

        ){
            Row{
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    text = title?: "Alert",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(
                    onClick = {
                        onButtonClick()
                    }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_exit),
                        contentDescription = ""
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = message?: "Unknown",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { onButtonClick() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Red,
                    contentColor = White
                ),
                border = null,
                shape = Shapes.medium,
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            ){
                Text(
                    text = buttonText?: "Close",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview()
@Composable
fun DialogPreview(
    title: String = "",
    onClose: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {}
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ){
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)

        ){
            Row{
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                IconButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_exit),
                        contentDescription = ""
                    )
                }
            }

            content()
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview()
@Composable
fun DialogFullScreen(
    title: String = "",
    onClose: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {}
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ){
        Column(
            modifier = Modifier
                .padding(start = 50.dp, end = 50.dp, top = 50.dp, bottom = 100.dp)
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
        ){
            Row{
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                IconButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_exit),
                        contentDescription = ""
                    )
                }
            }

            content()
        }
    }
}