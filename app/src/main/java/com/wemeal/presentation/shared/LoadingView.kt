package com.wemeal.presentation.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wemeal.presentation.intro.dimmedColor50
import com.wemeal.presentation.intro.purple600

@Preview
@Composable
private fun PreviewLoadingView() {
    LoadingView(true)
}

@Composable
fun LoadingView(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = dimmedColor50.copy(0.2f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color.Green.copy(alpha = 0.5f)
            )
        }
    }
}
