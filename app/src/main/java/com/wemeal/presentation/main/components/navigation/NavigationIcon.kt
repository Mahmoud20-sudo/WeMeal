package com.wemeal.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.wemeal.data.model.Screen


@OptIn(ExperimentalCoilApi::class)
@Composable
fun BottomIcon(screen: Screen) {
    val context = LocalContext.current
    if (!screen.selected.value) {
        Image(
            painterResource(screen.resourceId),
            contentDescription = null
        )
    } else {
        Image(
            rememberImagePainter(
                ContextCompat.getDrawable(
                    context,
                    screen.selectedResourceId
                )
            ),
            contentDescription = null
        )
    }
}