package core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.battambang_bold
import poscomposemultiplatform.composeapp.generated.resources.battambang_regular
import poscomposemultiplatform.composeapp.generated.resources.golos_text_bold
import poscomposemultiplatform.composeapp.generated.resources.golos_text_medium
import poscomposemultiplatform.composeapp.generated.resources.golos_text_regular
import poscomposemultiplatform.composeapp.generated.resources.kantumruy_bold
import poscomposemultiplatform.composeapp.generated.resources.kantumruy_medium
import poscomposemultiplatform.composeapp.generated.resources.kantumruy_regular
enum class Font {
    GolosText,
    KantumruyText,
    BattambangText,
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun getFontFamily(fontFamily: Font): FontFamily {

    val golosText = FontFamily(
        Font(Res.font.golos_text_bold, weight = FontWeight.Bold),
        Font(Res.font.golos_text_medium, weight = FontWeight.Medium),
        Font(Res.font.golos_text_regular, weight = FontWeight.Normal)
    )
    val kantumruyText = FontFamily(
        Font(Res.font.kantumruy_bold, weight = FontWeight.Bold),
        Font(Res.font.kantumruy_medium, weight = FontWeight.Medium),
        Font(Res.font.kantumruy_regular, weight = FontWeight.Normal)
    )
    val battambangText = FontFamily(
        Font(Res.font.battambang_bold, weight = FontWeight.Bold),
        Font(Res.font.battambang_regular, weight = FontWeight.Normal)
    )

    return when(fontFamily) {
        Font.GolosText -> {
            golosText
        }
        Font.KantumruyText -> {
            kantumruyText
        }
        Font.BattambangText -> {
            battambangText
        }
    }
}
@Composable
fun getTypography(): Typography {

    return Typography(
        displayLarge = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 50.sp,
        ),
        displayMedium = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 40.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 30.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 28.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 24.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = getFontFamily(Font.GolosText),
            fontWeight = FontWeight.W500,
            fontSize = 9.sp,
        ),
    )
}
