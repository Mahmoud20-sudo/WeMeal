package com.wemeal.presentation.intro.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wemeal.R
import com.wemeal.presentation.util.Dimensions
import com.wemeal.presentation.util.MediaQuery
import com.wemeal.presentation.util.greaterThan
import com.wemeal.presentation.util.lessThan

@Preview(name = "Title")
@Composable
fun PreviewImage() {
    ImageOnboarding(R.mipmap.intro_two)
}

@Composable
fun ImageOnboarding(@DrawableRes idDrawableRes: Int) {
    MediaQuery(comparator = Dimensions.Height greaterThan 600.dp) {
        Image(
            painterResource(idDrawableRes),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.wrapContentHeight().heightIn(max = 350.dp)
        )
    }
    MediaQuery(comparator = Dimensions.Height lessThan 600.dp) {
        Image(
            painterResource(idDrawableRes),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.wrapContentHeight().heightIn(max = 210.dp)
        )
    }
    MediaQuery(comparator = Dimensions.Height lessThan 500.dp) {
        Image(
            painterResource(idDrawableRes),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.wrapContentHeight().heightIn(max = 180.dp)
        )
    }
}