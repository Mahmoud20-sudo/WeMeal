package com.wemeal.presentation.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout

@Preview
@Composable
fun PreviewClickableConstrains() {
    ClickableConstrains(modifier = Modifier, onClick = {}) {}
}

@Composable
fun ClickableConstrains(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.clickable(
            enabled = true,
            onClickLabel = "",
            onClick = {
                onClick()
            })
    ) {
        content()
    }
}