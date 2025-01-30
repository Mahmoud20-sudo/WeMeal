package com.wemeal.presentation.main.components.posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.main.create.AwsResponseModel
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.main.components.grids.GridFour
import com.wemeal.presentation.main.components.grids.GridThree
import com.wemeal.presentation.main.components.grids.GridTwo
import com.wemeal.presentation.shared.ClickableCoilImage
import com.wemeal.presentation.util.*
import java.io.File

@ExperimentalCoilApi
@Preview()
@Composable
fun PreviewImage() {
    PostImage(
        imgs = listOf("", ""), onImageClick = { _: Int, _: List<String> -> }
    )
}

@ExperimentalCoilApi
@Composable
fun PostImage(
    imgs: List<String>,
    isFeed: Boolean = false,
    onImageClick: (imgIndex: Int, imgs: List<String>) -> Unit = { i, list -> },
//    onCloseClick: () -> Unit = {}
) {

    MediaQuery {
        when (imgs.size) {
            1 -> {
                val imageUrl = if (File(imgs[0]).exists()) File(imgs[0]) else imgs[0]
                val imagePainter = rememberImagePainter(data = imageUrl)
                val state = imagePainter.state

                ClickableCoilImage(
                    painter = imagePainter,
                    placeholder = if(!isFeed) R.drawable.ic_clear else 0,
                    contentDescription = ImageActions.VIEW.contentDescription,
                    contentScale = ContentScale.Crop,
                    alignment = androidx.compose.ui.Alignment.Center,           // crop the image if it's not a square
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .placeholder(
                            visible = state is ImagePainter.State.Loading || state is ImagePainter.State.Empty,
                            color = lightSky,
                            // optional, defaults to RectangleShape
                            shape = RoundedCornerShape(4.dp),
                            highlight = PlaceholderHighlight.fade(
                                highlightColor = Color.White,
                            ),
                        ),
                    onImageClick = {
                        onImageClick(0, imgs)
                    }
                )
            }
            2 -> GridTwo(
                imgs = imgs
            ) { index ->
                onImageClick(index, imgs)
            }
            3 -> GridThree(
                imgs,
            ) { index ->
                onImageClick(index, imgs)
            }
            else -> GridFour(imgsList = imgs) { index ->
                onImageClick(index, imgs)
            }
        }
    }
}