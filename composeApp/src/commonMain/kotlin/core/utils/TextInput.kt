package core.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    isInputPrice: Boolean = false,
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
    var input by remember { mutableStateOf(text) }

    CompositionLocalProvider(value = LocalTextSelectionColors provides textInputSelectionColors){
        BasicTextField(
            enabled = enabled,
            modifier = modifier,
            interactionSource = interactionSource,
            value = input,
            onValueChange = { newText ->
                val isDecimal = keyboardType == KeyboardType.Decimal
                input = newText.replace(",","")
                if (isDecimal){
                    if(newText.split(".").size > 2) return@BasicTextField

                    //disable input start with '.'
                    if (input.length == 1 && input.contains('.')){
                        input = ""
                    }
                    if (input.length == 2){
                        if (input == "00"){
                            //disable input zero for second digits
                            input = "0"
                        } else {
                            //auto removed zero for second digits
                            if (input == "01" || input == "02" || input == "03"
                                || input == "04" || input == "05" || input == "06"
                                || input == "07" || input == "08" || input == "09"
                            )  {
                                input = input.removeRange(0, 1)
                            }
                        }
                    }

                    // Split into integer and fractional parts
                    val parts = input.split('.')
                    val fractionalPart = parts.getOrElse(1) { "0" }
                    if (fractionalPart.length > 2){
                        input = input.dropLast(1)
                    }
                }
                onValueChange(input)
            },
            visualTransformation = CursorVisualTransformation().takeIf { isInputPrice } ?: VisualTransformation.None,
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
    isInputPrice: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    var input by remember { mutableStateOf(text) }

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
        onValueChange = { newText ->
            val isDecimal = keyboardType == KeyboardType.Decimal
            input = newText.replace(",","")
            if (isDecimal){
                if(newText.split(".").size > 2) return@OutlinedTextField

                //disable input start with '.'
                if (input.length == 1 && input.contains('.')){
                    input = ""
                }
                if (input.length == 2){
                    if (input == "00"){
                        //disable input zero for second digits
                        input = "0"
                    } else {
                        //auto removed zero for second digits
                        if (input == "01" || input == "02" || input == "03"
                            || input == "04" || input == "05" || input == "06"
                            || input == "07" || input == "08" || input == "09"
                        )  {
                            input = input.removeRange(0, 1)
                        }
                    }
                }

                // Split into integer and fractional parts
                val parts = input.split('.')
                val fractionalPart = parts.getOrElse(1) { "0" }
                if (fractionalPart.length > 2){
                    input = input.dropLast(1)
                }
            }
            onValueChange(input)
        },
        visualTransformation = CursorVisualTransformation().takeIf { isInputPrice } ?: VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
    )
}

@Composable
fun TextFieldWrapper(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String? = null,
    valueColor: Color = Color.Black,
    enableInput: Boolean = true,
    singleLine: Boolean = true,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        shape = Shapes.medium,
        value = value,
        onValueChange = onValueChange,
        enabled = enableInput,
        textStyle = TextStyle(fontSize = fontSizeContent),
        colors = textInputColors,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions
    )
}