package com.wemeal.presentation.main.components.createpost

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import coil.annotation.ExperimentalCoilApi
import com.wemeal.data.model.main.Result
import com.wemeal.presentation.main.components.createpost.mentions.MentionItem

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewMentionsWindow() {
    MentionsWindow(
        modifier = Modifier,
        loading = false,
        usersList = mutableListOf(),
        onItemClicked = {},
        popupHeight = 100.dp
    )
}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun MentionsWindow(
    modifier: Modifier,
    loading: Boolean,
    popupHeight: Dp,
    usersList: MutableList<Result>?,
    onItemClicked: (index: Int) -> Unit
) {
//    Popup(
//        alignment = Alignment.Center
//    ) {

    Column {
        for (index in usersList?.indices!!)
            MentionItem(
                user = if (usersList.isNullOrEmpty()) null else usersList[index],
                isShimmerEffect = loading,
                modifier = Modifier.clickable(onClick = { //handle onClick
                    onItemClicked(index)
                })
            )
    }


//        LazyColumn {
//            items(
//                if (usersList.isNullOrEmpty()) 10 else usersList.size
//            ) { index ->
//                MentionItem(
//                    user = if (usersList.isNullOrEmpty()) null else usersList[index],
//                    isShimmerEffect = loading,
//                    modifier = Modifier.clickable(onClick = { //handle onClick
//                        onItemClicked(index)
//                    })
//                )
//            }
//      }
//    }
}