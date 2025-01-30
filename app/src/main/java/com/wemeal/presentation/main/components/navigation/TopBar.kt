package com.wemeal.presentation.main.components.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wemeal.R

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewTopBar() {
    TopBar()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier.height(56.dp),
        backgroundColor = Color.White,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .width(59.dp)
                    .height(15.dp),
                painter = painterResource(id = R.mipmap.ic_logo),
                contentDescription = "SEARCH"
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Image(
                modifier = Modifier
                    .padding(end = 28.dp)
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.ic_bar_search),
                contentDescription = "SEARCH"
            )

            Image(
                modifier = Modifier
                    .padding(end = 28.dp)
                    .width(16.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "NOTIFICATION"
            )

            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "CART"
            )
        }
    }
}