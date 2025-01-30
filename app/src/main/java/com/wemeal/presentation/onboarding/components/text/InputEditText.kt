package com.wemeal.presentation.onboarding.components.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.presentation.intro.dimmedColor

@ExperimentalUnitApi
@Preview
@Composable
fun PreviewInputEditText() {
    InputEditText(
        value = "",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 9.7.dp),
        onValueChange = {},
        maxLines = 1,
        contentTextStyle = TextStyle(
            fontSize = 12.sp,
            color = Color.Black,
            letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
            fontWeight = FontWeight.Normal,
        ),
        hintTextStyle = TextStyle(
            color = dimmedColor,
            fontSize = 12.sp,
            letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
            fontWeight = FontWeight.Normal,
        )
    )
}

@Composable
fun InputEditText(
    value: String = "",
    annotatedString: AnnotatedString = AnnotatedString(""),
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    contentTextStyle: TextStyle,
    hintTextStyle: TextStyle,
    placeHolderString: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cursorColor: Color = Color.Black,
    visualTransformation : VisualTransformation = VisualTransformation.None
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = contentTextStyle,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        if (contentTextStyle.textAlign == TextAlign.Start)
                            IntOffset(x = 10, y = 0)
                        else
                            IntOffset(x = 0, y = 0)
                    },
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeHolderString,
                        color = hintTextStyle.color,
                        fontSize = hintTextStyle.fontSize
                    )
                }

                innerTextField()
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor),
        visualTransformation = visualTransformation
    )
}
