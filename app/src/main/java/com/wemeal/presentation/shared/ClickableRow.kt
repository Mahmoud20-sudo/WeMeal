package com.wemeal.presentation.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PreviewClickableRow() {
    ClickableRow(modifier = Modifier, onClick = {}) {}
}

@Composable
fun ClickableRow(
    modifier: Modifier,
    onClick: () -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.clickable(
            enabled = true,
            onClickLabel = "",
            onClick = {
                onClick()
            }),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        content()
    }
}