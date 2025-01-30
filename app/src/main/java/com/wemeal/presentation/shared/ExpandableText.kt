package com.wemeal.presentation.shared

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.purple300

@ExperimentalAnimationApi
@OptIn(ExperimentalUnitApi::class)
@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 1,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }
    val context = LocalContext.current

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
//        textLayoutResult?.lineCount.let {
//            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
//        }

//        if (textLayoutResult != null && seeMoreSize != null) {
//
//            if(textLayoutResult.lineCount < minimizedMaxLines  )
//                temp =  minimizedMaxLines - 1
//            else
//                temp = textLayoutResult.lineCount - 1
//
//            Toast.makeText(context, temp.toString(), Toast.LENGTH_SHORT)
//
//        }

        if (textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier) {
        Text(
            text = cutText ?: text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState.value = it },
        )
        val density = LocalDensity.current
        ClickableText(
            text = stringResource(
                id = if (!expanded) R.string.show_more else R.string.show_less
            ),
            color = purple300,
            modifier = Modifier
                .then(
                    if (seeMoreOffset != null)
                        Modifier.offset(
                            x = with(density) { seeMoreOffset.x.toDp() },
                            y = with(density) { seeMoreOffset.y.toDp() },
                        )
                    else
                        Modifier
                )
                .alpha(if (seeMoreOffset != null) 1f else 0f),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            expanded = !expanded
            cutText = null
        }

//        Text(
//            stringResource(id = if (!expanded) R.string.show_more else R.string.show_less),
//            color = purple300,
//            fontSize = 12.sp,
//            style = TextStyle(
//                fontWeight = FontWeight.Bold
//            ), onTextLayout = { seeMoreSizeState.value = it.size },
//            modifier = Modifier
//                .then(
//                    if (seeMoreOffset != null)
//                        Modifier.offset(
//                            x = with(density) { seeMoreOffset.x.toDp() },
//                            y = with(density) { seeMoreOffset.y.toDp() },
//                        )
//                    else
//                        Modifier
//                )
//                .clickable {
//                    expanded = !expanded
//                    cutText = null
//                }
//                .alpha(if (seeMoreOffset != null) 1f else 0f)
//        )
    }
}