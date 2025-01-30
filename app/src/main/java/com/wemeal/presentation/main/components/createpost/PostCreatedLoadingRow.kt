package com.wemeal.presentation.main.components.createpost

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.presentation.intro.black300
import com.wemeal.presentation.intro.purple300
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.montserratMedium

@ExperimentalUnitApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewPostCreatedLoadingRow() {
    PostCreatedLoadingRow(
        onClick = {},
        viewModel = null
    )
}

@ExperimentalUnitApi
@Composable
fun PostCreatedLoadingRow(
    viewModel: MainViewModel?,
    onClick: () -> Unit
) {
    val createPostLiveData by viewModel?.createPostLiveData!!.observeAsState()

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )
    AnimatedVisibility(visible = createPostLiveData !is Resource.Success) {
        Row(
            modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (createPostLiveData is Resource.Error
                ) painterResource(R.drawable.ic_warning) else painterResource(
                    R.drawable.ic_dashed_loading
                ),
                contentDescription = "",
                modifier = Modifier
                    .width(19.dp)
                    .height(17.dp)
                    .graphicsLayer {
                        if (createPostLiveData is Resource.Loading)
                            rotationZ = angle
                    }
            )

            Text(
                text = if (createPostLiveData is Resource.Error) stringResource(id = R.string.posting_failed)
                else "${viewModel?.postingText?.value}",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratMedium),
                    fontSize = 14.sp,
                    color = black300,
                    letterSpacing = TextUnit(-0.42f, TextUnitType.Sp)
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 9.dp)
            )
            AnimatedVisibility(visible = createPostLiveData is Resource.Error) {
                ClickableText(
                    text = stringResource(id = R.string.try_again),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratMedium),
                        fontSize = 12.sp,
                        color = purple300
                    ),
                    modifier = Modifier
                ) {
                    onClick.invoke()
                }
            }
        }
    }
}