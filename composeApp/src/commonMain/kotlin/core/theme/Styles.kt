package core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val fontSizeTitle = 14.sp
val fontSizeContent = 12.sp
val textInputWidth = 200.dp
//val textInputColors @Composable get() = TextFieldDefaults.colors(
//    focusedIndicatorColor = Primary,
//    focusedContainerColor = White,
//    unfocusedIndicatorColor = Color(0x26000000),
//    unfocusedContainerColor = White,
//    disabledContainerColor = White,
//    cursorColor = Primary,
//)

val hintTextColor = Color(0xFF9E9E9E)
val labelTextColor = Color(0xFF757373)

//val hintTextStyle = TextStyle(
//    fontSize = fontSizeContent,
//    fontWeight = FontWeight.Normal,
//    fontFamily = GolosText,
//    color = hintTextColor
//)

//val labelTextStyle = TextStyle(
//    fontSize = fontSizeContent,
//    fontWeight = FontWeight.Normal,
//    fontFamily = GolosText,
//    color = labelTextColor
//)

//val textInputSelectionColors = TextSelectionColors(
//    handleColor = Primary,
//    backgroundColor = Color(0x4D0F38EE),
//)

enum class Styles {
    HeaderLarge,
    HeaderMedium,
    HeaderSmall,
    TitleLarge,
    TitleMedium,
    TitleSmall,
    DisplayLarge,
    DisplayMedium,
    DisplaySmall,
    BodyLarge,
    BodyMedium,
    BodySmall,
    LabelLarge,
    LabelMedium,
    LabelSmall
}

@Composable
fun textStylePrimary12Normal(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = PrimaryColor
    )
}

@Composable
fun textStyleBlack14Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack25Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack25Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Medium,
        fontSize = 25.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack25Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack30Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack30Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack30Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack20Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack20Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack20Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack17Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack17Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack17Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack15Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack15Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack15Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack13Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack13Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack13Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack12Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack12Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack12Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Black
    )
}
@Composable
fun textStyleBlack14Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Black
    )
}

@Composable
fun textStyleBlack14Small(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Black
    )
}
@Composable
fun textStyleBlack10Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.BattambangText),
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        color = Black,
    )
}
@Composable
fun textStyleKantumruyBlack14Bold(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.KantumruyText),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Black
    )
}
@Composable
fun textStyleKantumruyBlack10Medium(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.KantumruyText),
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        color = Black
    )
}
@Composable
fun textStyleKantumruyBlack10Regular(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.KantumruyText),
        fontWeight = FontWeight.W400,
        fontSize = 10.sp,
        color = Black
    )
}
@Composable
fun textStyleKantumruy9Regular6D7278(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.KantumruyText),
        fontWeight = FontWeight.W400,
        fontSize = 9.sp,
        color = Color6D7278
    )
}

@Composable
fun textStyleKantumruy9MediumBlack(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.KantumruyText),
        fontWeight = FontWeight.W500,
        fontSize = 9.sp,
        color = Black
    )
}
@Composable
fun textStyle9RegularBlack(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.W500,
        fontSize = 9.sp,
        color = Black
    )
}
@Composable
fun textStyle9MediumBlack(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.W500,
        fontSize = 9.sp,
        color = Black
    )
}
@Composable
fun textStyle12BoldBlack(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.GolosText),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Black
    )
}
@Composable
fun textStyleKantumruy12BoldBlack(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.KantumruyText),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Black
    )
}
@Composable
fun textStyleKantumruy11BoldBlack(): TextStyle {
    return TextStyle(
        fontFamily = getFontFamily(core.theme.Font.KantumruyText),
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        color = Black
    )
}