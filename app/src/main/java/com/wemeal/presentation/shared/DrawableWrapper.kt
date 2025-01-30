package com.wemeal.presentation.shared

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DrawableWrapper(
    modifier: Modifier = Modifier,
    @DrawableRes drawableTop: Int? = null,
    @DrawableRes drawableStart: Int? = null,
    @DrawableRes drawableBottom: Int? = null,
    @DrawableRes drawableEnd: Int? = null,
    width: Dp,
    height: Dp,
    content: @Composable () -> Unit,
) {
    ConstraintLayout(modifier) {
        val (refImgStart, refImgTop, refImgBottom, refImgEnd, refContent) = createRefs()
        Box(Modifier.constrainAs(refContent) {
            top.linkTo(drawableTop?.let { refImgTop.bottom } ?: parent.top)
            bottom.linkTo(drawableBottom?.let { refImgBottom.top } ?: parent.bottom)
            start.linkTo(drawableStart?.let { refImgStart.end } ?: parent.start)
            end.linkTo(drawableEnd?.let { refImgEnd.start } ?: parent.end)
        }) {
            content()
        }
        drawableTop?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(refImgTop) {
                    top.linkTo(parent.top)
                    start.linkTo(refContent.start)
                    end.linkTo(refContent.end)
                }.width(width = width).height(height = height)
            )
        }
        drawableStart?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(refImgStart) {
                    top.linkTo(refContent.top)
                    bottom.linkTo(refContent.bottom)
                    start.linkTo(parent.start)
                }.width(width = width).height(height = height)
            )
        }
        drawableBottom?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(refImgBottom) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(refContent.start)
                    end.linkTo(refContent.end)
                }.width(width = width).height(height = height)
            )
        }
        drawableEnd?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(refImgEnd) {
                    top.linkTo(refContent.top)
                    bottom.linkTo(refContent.bottom)
                    end.linkTo(parent.end)
                }.width(width = width).height(height = height).padding(start = 4.dp)
            )
        }
    }
}