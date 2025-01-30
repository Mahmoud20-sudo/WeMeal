package com.wemeal.presentation.main.components.dialog

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.FoodyModel
import com.wemeal.data.model.MoreModel
import com.wemeal.data.model.main.feed.Activity
import com.wemeal.presentation.intro.lightBlack
import com.wemeal.presentation.main.components.bottomsheet.MoreSheetContent
import com.wemeal.presentation.shared.BottomSheetLayout

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewMoreActionsDialog() {
    MoreActionsDialog(
        items = listOf(), onDismiss = {}
    )
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoreActionsDialog(
    items: List<MoreModel>,
    onDismiss: (index: Int) -> Unit
) {
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss(-1)
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .clickable(
                    enabled = true,
                    onClickLabel = "DISMISS DIALOG",
                    onClick = {
                        onDismiss(-1)
                    }
                )
                .background(
                    lightBlack.copy(0.3f),
                )
        ) {
            BottomSheetLayout(
                modifier = Modifier
                    .height(400.dp)
                    .align(BottomCenter),
                shape = RoundedCornerShape(
                    topEnd = 24.dp,
                    topStart = 24.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                ),
                bottomState = bottomState
            ) {
                MoreSheetContent(items) { index ->
                    onDismiss(index)
                }
            }
        }
    }
}



