package com.wemeal.presentation.onboarding.components.bottomsheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wemeal.R

@ExperimentalAnimationApi
@Composable
fun AnimatedBack(visible: Boolean,
                 onClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier
            .clickable(
                enabled = true,
                onClickLabel = "End Search",
                onClick = {
                    onClick.invoke()
                }
            ),
        visible = visible,
    ) {
        Image(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .padding(3.dp),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back",
            contentScale = ContentScale.FillBounds
        )
    }
}