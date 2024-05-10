package core.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.fontSizeContent
import core.theme.hintTextStyle
import core.theme.textInputColors
import core.theme.textInputSelectionColors
import core.theme.textInputWidth


@Composable
fun TextInputNormal(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val borderColor = if(isFocused.value) PrimaryColor else Color(0x26000000)
    var input by rememberSaveable { mutableStateOf(text) }

    CompositionLocalProvider(value = LocalTextSelectionColors provides textInputSelectionColors){
        BasicTextField(
            enabled = enabled,
            modifier = modifier,
            interactionSource = interactionSource,
            value = input,
            onValueChange = { newText ->
                if(keyboardType == KeyboardType.Decimal && newText.split(".").size > 2)
                    return@BasicTextField

                input = newText
                onValueChange(newText)
            },
            cursorBrush = SolidColor(PrimaryColor),
            textStyle = TextStyle(fontSize = fontSizeContent),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .widthIn(min = textInputWidth + 32.dp)
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = Shapes.medium
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                ) {
                    if (input.isEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = placeholder,
                            style = hintTextStyle
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun TextInputNormalShorter(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val borderColor = if(isFocused.value) PrimaryColor else Color(0x26000000)
    var input by rememberSaveable { mutableStateOf(text) }

    CompositionLocalProvider(value = LocalTextSelectionColors provides textInputSelectionColors){
        BasicTextField(
            modifier = modifier,
            interactionSource = interactionSource,
            value = input,
            onValueChange = { newText ->
                if(keyboardType == KeyboardType.Decimal && newText.split(".").size > 2)
                    return@BasicTextField

                input = newText
                onValueChange(newText)
            },
            cursorBrush = SolidColor(PrimaryColor),
            textStyle = TextStyle(fontSize = fontSizeContent),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .widthIn(min = 100.dp + 32.dp)
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = Shapes.medium
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                ) {
                    if (input.isEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = placeholder,
                            style = hintTextStyle
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}


@Composable
fun TextInputDefault(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    var input by rememberSaveable { mutableStateOf(text) }

    OutlinedTextField(
        modifier = modifier,
        shape = Shapes.medium,
        placeholder = {
            Text(
                text = placeholder,
                style = hintTextStyle
            )
        },
        textStyle = TextStyle(fontSize = fontSizeContent),
        colors = textInputColors,
        value = input,
        onValueChange = {
            if(keyboardType == KeyboardType.Decimal && it.split(".").size > 1)
                return@OutlinedTextField

            input = it
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
    )
}