package core.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import core.theme.Black
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.theme.fontSizeContent
import core.theme.hintTextStyle
import core.theme.textInputColors
import core.theme.textInputSelectionColors
import core.theme.textInputWidth


@Composable
fun TextInputDropdown(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    enabled: Boolean = true,
    enabledInput: Boolean = true,
    list: List<String> = arrayListOf(),
    onValueChange: (String) -> Unit = {},
    onSelectChange: (Int) -> Unit = {},
){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val borderColor = if(isFocused.value) PrimaryColor else Color(0x26000000)

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Box{
        CompositionLocalProvider(LocalTextSelectionColors provides textInputSelectionColors){
            BasicTextField(
                enabled = enabled && enabledInput,
                modifier = modifier
                    .clip(shape = Shapes.medium)
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                cursorBrush = SolidColor(PrimaryColor),
                interactionSource = interactionSource,
                value = selectedText,
                onValueChange = { newText ->

                    if(keyboardType == KeyboardType.Decimal && newText.split(".").size > 2)
                        return@BasicTextField

                    selectedText = newText
                    onValueChange(newText)
                },
                textStyle = TextStyle(fontSize = fontSizeContent),
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction,
                    keyboardType = keyboardType,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .widthIn(min = textInputWidth)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = Shapes.medium
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                    ) {
                        if (selectedText.isEmpty()) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterStart),
                                text = placeholder,
                                style = hintTextStyle
                            )
                        }

                        Box(
                            modifier = Modifier
                                .widthIn(min = textInputWidth)
                                .align(Alignment.Center),
                        ){
                            innerTextField()
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = "",
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.CenterEnd)
                                .clickable(
                                    enabled = enabled,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { expanded = !expanded }
                        )
                    }
                }
            )
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(White)
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            list.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = item
                        expanded = !expanded
                        onSelectChange(index)
                    },
                    text = {
                        Text(
                            text =  item,
                            style = TextStyle(
                                fontSize = fontSizeContent,
                                fontWeight = FontWeight.Normal,
                                color = Black,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun TextInputDropdownDefault(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    list: List<String> = arrayListOf(),
    onValueChange: (String) -> Unit = {},
    onSelectCustomer: (Int) -> Unit = {},
){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val focusManager = LocalFocusManager.current
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Box{
        OutlinedTextField(
            enabled = enabled,
            shape = Shapes.medium,
            placeholder = {
                Text(
                    text = placeholder,
                    style = hintTextStyle
                )
            },
            textStyle = TextStyle(fontSize = fontSizeContent),
            colors = textInputColors,
            value = selectedText,
            onValueChange = {
                if(keyboardType == KeyboardType.Decimal && it.split(".").size > 1)
                    return@OutlinedTextField
                selectedText = it
                onValueChange(it)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = Black,
                    modifier = Modifier
                        .clickable(
                            enabled = enabled,
                            onClick = {
                                expanded = !expanded
                            }
                        )
                )
            },
            modifier = Modifier
                .clip(shape = Shapes.medium)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(White)
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            list.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = item
                        expanded = !expanded
                        onSelectCustomer(index)
                    },
                    text = {
                        Text(
                            text =  item,
                            style = TextStyle(
                                fontSize = fontSizeContent,
                                fontWeight = FontWeight.Normal,
                                color = Black,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                )
            }
        }
    }
}