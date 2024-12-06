package core.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.theme.fontSizeContent
import core.theme.labelTextStyle


@Composable
fun TextRequire(label: String = "Label"){
    if(label.isNotEmpty()){
        Row {
            Text(
                text = label,
                style = labelTextStyle
            )

            Text(
                text = "*",
                style = TextStyle(
                    fontSize = fontSizeContent,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red
                )
            )
        }
    }
}

@Composable
fun LabelInputRequire(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "Label",
    isInputPrice: Boolean = false,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit = {}
){
    Column {
        TextRequire(label)
        Spacer(modifier = Modifier.height(8.dp))
        TextInputNormal(
            modifier = modifier,
            text = text,
            isInputPrice = isInputPrice,
            placeholder = placeholder,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onValueChange = { onValueChange(it) }
        )
    }
}


@Composable
fun LabelInputNormal(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "Label",
    placeholder: String = "",
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit = {}
){
    Column {
        Text(
            text = label,
            style = labelTextStyle
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextInputNormal(
            modifier = modifier,
            text = text,
            enabled = enabled,
            placeholder = placeholder,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onValueChange = { onValueChange(it) }
        )
    }
}


@Composable
fun LabelInputNormalShorter(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "Label",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit = {}
){
    Column {
        Text(
            text = label,
            style = labelTextStyle
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextInputNormalShorter(
            modifier = modifier,
            text = text,
            placeholder = placeholder,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onValueChange = { onValueChange(it) }
        )
    }
}


@Composable
fun LabelSelectInput(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "",
    placeholder: String = "",
    enabledInput: Boolean = true,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    list: List<String> = arrayListOf(),
    onValueChange: (String) -> Unit = {},
    onSelectChange: (Int) -> Unit = {},
){
    Column {
        Text(
            text = label,
            style = labelTextStyle
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextInputDropdown(
            modifier = modifier,
            enabled = enabled,
            enabledInput = enabledInput,
            text = text,
            placeholder = placeholder,
            keyboardType = keyboardType,
            imeAction = imeAction,
            list = list,
            onValueChange = { onValueChange(it) },
            onSelectChange = { onSelectChange(it) }
        )
    }
}