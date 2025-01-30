package com.wemeal.presentation.main.components.posts

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wemeal.R
import android.widget.TextView.BufferType
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.compose.ui.viewinterop.AndroidView
import android.text.TextPaint
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat

@Preview
@Composable
fun PreviewSharedPostNameBox() {
    SharedPostNameBox(
        modifier = Modifier,
        nameOneText = "Bissan Mohamed",
        nameTwoText = "Chicken Chester",
        20.sp,
        R.font.montserrat_semibold,
        firstNameClick = {}
    ) {}
}

@SuppressLint("InflateParams")
@Composable
fun SharedPostNameBox(
    modifier: Modifier, nameOneText: String, nameTwoText: String,
    fontSize: TextUnit,
    font: Int,
    firstNameClick: (name: String) -> Unit = {},
    secondNameClick: (name: String) -> Unit = {}
) {
    Box(modifier = modifier) {
        AndroidView(
            factory = { context: Context ->
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_tagged_post_name, null, false)
                val textView = view.findViewById<AppCompatTextView>(R.id.name_tv)
                // do whatever you want...
                textView.textSize = fontSize.value
                textView.typeface = ResourcesCompat.getFont(context, font)
                setText(
                    context,
                    textView,
                    nameOneText,
                    nameTwoText,
                    firstNameClick,
                    secondNameClick
                )
                view // return the view
            }
        )
    }
}

private fun setText(
    context: Context,
    textView: TextView,
    nameOneText: String,
    nameTwoText: String,
    firstNameClick: (name: String) -> Unit,
    secondNameClick: (name: String) -> Unit
) {
    val spanText = SpannableStringBuilder()
    spanText.append(nameOneText)
    val firstNameClickSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            firstNameClick(nameOneText)
        }

        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.isUnderlineText = false // this remove the underline
        }
    }
//    spanText.append(" \u276F ")

    val span =
        ImageSpan(context, R.drawable.ic_keyboard_arrow_right, DynamicDrawableSpan.ALIGN_BASELINE)

    spanText.append("\t\t")
    spanText.setSpan(
        span,
        spanText.length - 1,
        spanText.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spanText.append("\t")

    spanText.append(nameTwoText)
    val secondNameClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            secondNameClick(nameTwoText)
        }

        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.isUnderlineText = false // this remove the underline
        }
    }

    spanText.setSpan(firstNameClickSpan, 0, nameOneText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spanText.setSpan(
        secondNameClickableSpan,
        spanText.length - nameTwoText.length,
        spanText.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    textView.linksClickable = true;
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.setText(spanText, BufferType.SPANNABLE)
}


