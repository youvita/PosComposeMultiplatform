package core.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object InputUtil {

    fun limitedDecimal(input: String, last: Int): String {
        val text: String
        if (last == 0) {
            val filteredChars = input.filterIndexed { _, c ->
                c in "0123456789"
            }
            text = filteredChars.take(input.length)
        } else {
            val filteredChars = input.filterIndexed { index, c ->
                c in "0123456789" || (c == '.' && input.indexOf('.') == index)
            }
            text = if (filteredChars.contains('.')) {
                val beforeDecimal = filteredChars.substringBefore('.')
                val afterDecimal = filteredChars.substringAfter('.')
                beforeDecimal.take(input.length) + "." + afterDecimal.take(last)
            } else {
                filteredChars.take(input.length)
            }
        }
        return text
    }

    class CardVisualTransformation : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
            var out = ""
            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i % 4 == 3 && i != 15) out += "-"
            }

            val creditCardOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 7) return offset + 1
                    if (offset <= 11) return offset + 2
                    if (offset <= 16) return offset + 3
                    return 19
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 4) return offset
                    if (offset <= 9) return offset - 1
                    if (offset <= 14) return offset - 2
                    if (offset <= 19) return offset - 3
                    return 16
                }
            }
            return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
        }
    }

//    class NoneDecimalVisualTransformation : VisualTransformation {
//
//        override fun filter(text: AnnotatedString): TransformedText {
//            val inputText = text.text
//
//            val intPart = inputText
//                .reversed()
//                .chunked(3)
//                .joinToString(groupingSymbol.toString())
//                .reversed()
//
//            val newText = AnnotatedString(
//                text = intPart,
//                spanStyles = text.spanStyles,
//                paragraphStyles = text.paragraphStyles
//            )
//
//            val offsetMapping = MovableCursorOffsetMapping(
//                unmaskedText = text.toString(),
//                maskedText = newText.toString(),
//                decimalDigits = 0
//            )
//            return TransformedText(newText, offsetMapping = offsetMapping)
//        }
//
//        private class MovableCursorOffsetMapping(
//            private val unmaskedText: String,
//            private val maskedText: String,
//            private val decimalDigits: Int
//        ) : OffsetMapping {
//            override fun originalToTransformed(offset: Int): Int =
//                when {
//                    unmaskedText.length <= decimalDigits -> {
//                        maskedText.length - (unmaskedText.length - offset)
//                    }
//                    else -> {
//                        offset + offsetMaskCount(offset, maskedText)
//                    }
//                }
//
//            override fun transformedToOriginal(offset: Int): Int =
//                when {
//                    unmaskedText.length <= decimalDigits -> {
//                        Integer.max(unmaskedText.length - (maskedText.length - offset), 0)
//                    }
//                    else -> {
//                        offset - maskedText.take(offset).count { !it.isDigit() }
//                    }
//                }
//
//            private fun offsetMaskCount(offset: Int, maskedText: String): Int {
//                var maskOffsetCount = 0
//                var dataCount = 0
//                for (maskChar in maskedText) {
//                    if (!maskChar.isDigit()) {
//                        maskOffsetCount++
//                    } else if (++dataCount > offset) {
//                        break
//                    }
//                }
//                return maskOffsetCount
//            }
//        }
//    }

//    class DecimalVisualTransformation : VisualTransformation {
//
//        override fun filter(text: AnnotatedString): TransformedText {
//            val inputText = text.text
//
//            val intPart = inputText.split(decimalSymbol)
//
//            val hasEndDot = inputText.endsWith('.')
//            var formatted = inputText
//
//            val annotatedString = buildAnnotatedString {
//
//                if (text.isNotEmpty() && intPart.size == 1) {
//                    formatted = numberFormatter.format(BigDecimal(intPart[0]))
//
//                    if (hasEndDot) {
//                        formatted += decimalSymbol
//                    }
//                } else if (intPart.size == 2) {
//                    if (intPart[0].isNotEmpty()) {
//                        val numberPart = numberFormatter.format(BigDecimal(intPart[0]))
//                        val decimalPart = intPart[1]
//
//                        formatted = "$numberPart.$decimalPart"
//                    }
//                }
//                append(formatted)
//            }
//
//            val originalToTransformed = mutableListOf<Int>()
//            val transformedToOriginal = mutableListOf<Int>()
//            var specialCharsCount = 0
//
//            annotatedString.forEachIndexed { index, char ->
//                if (groupingSymbol == char) {
//                    specialCharsCount++
//                } else {
//                    originalToTransformed.add(index)
//                }
//                transformedToOriginal.add(index - specialCharsCount)
//            }
//
//            originalToTransformed.add(originalToTransformed.maxOrNull()?.plus(1) ?: 0)
//            transformedToOriginal.add(transformedToOriginal.maxOrNull()?.plus(1) ?: 0)
//
//            return TransformedText(
//                annotatedString,
//                object : OffsetMapping {
//                    override fun originalToTransformed(offset: Int): Int {
//                        return originalToTransformed[offset]
//                    }
//
//                    override fun transformedToOriginal(offset: Int): Int {
//                        return transformedToOriginal[offset]
//                    }
//                },
//            )
//        }
//    }

    class PhoneVisualTransformation(val mask: String, val maskNumber: Char, val startZero: Boolean): VisualTransformation {

        private val maxLength = mask.count { it == maskNumber }

        override fun filter(text: AnnotatedString): TransformedText {

            var trimmed = if (text.length > maxLength) text.take(maxLength) else text

            val annotatedString = buildAnnotatedString {
                if (trimmed.isEmpty()) return@buildAnnotatedString

                var maskIndex = 0
                var textIndex = 0
                if (trimmed.first() == '0' && trimmed.length == 3) {
                    trimmed = trimmed.removeRange(0, 1)
                    append(trimmed[0])
                    append(trimmed[1])
                    append("-")
                } else {
                    if (trimmed.first() == '0' && trimmed.length >= 4) trimmed = trimmed.removeRange(0, 1)
                    while (textIndex < trimmed.length && maskIndex < mask.length) {
                        if (mask[maskIndex] != maskNumber) {
                            val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                            append(mask.substring(maskIndex, nextDigitIndex))
                            maskIndex = nextDigitIndex
                        }
                        append(trimmed[textIndex++])
                        maskIndex++
                    }
                }
            }

            println(annotatedString)
            return TransformedText(annotatedString, object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (startZero) {
                        if (offset <= 6) return offset
                        if (offset <= 9) return offset + 1
                        if (offset <= 12) return offset + 2
                        if (offset <= 15) return offset + 3
                    } else {
                        if (offset <= 2) return offset
                        if (offset <= 5) return offset + 1
                        if (offset <= 8) return  offset + 2
                        if (offset <= 11) return  offset + 3
                    }
                    return offset
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return offset - mask.take(offset).count { it != maskNumber }
                }
            })
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is PhoneVisualTransformation) return false
            if (mask != other.mask) return false
            if (maskNumber != other.maskNumber) return false
            return true
        }

        override fun hashCode(): Int {
            return mask.hashCode()
        }
    }
}