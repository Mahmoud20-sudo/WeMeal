package com.wemeal.presentation.shared

import android.content.Context
import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.wemeal.R
import com.wemeal.presentation.util.ExpandableTextView
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.wemeal.data.model.user.UserModel


@Composable
fun ExpandableTextView(
    modifier: Modifier = Modifier,
    text: String,
    onCaptionClick: () -> Unit = {},
    onShowClick: (isShowMore: Boolean) -> Unit = {},
    mentionsList: List<UserModel>?
) {
//    val currentContext = LocalContext.current
    Box(modifier = modifier) {
        AndroidView(
            factory = { context: Context ->
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.expandable_textview, null, false)
                val textView = view.findViewById<ExpandableTextView>(R.id.expand_tv)
                // do whatever you want...
                val typeface: Typeface? = ResourcesCompat.getFont(context, R.font.montserrat_regular)
                textView.typeface = typeface
                textView.setMentionList(mentionsList)
                textView.setOnCaptionClickListener(onCaptionClick)
                textView.setOnShowClickListener(onShowClick)

                textView.text = text
                view // return the view
            }
        )
    }
}
