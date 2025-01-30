package com.wemeal.presentation.main.components.grids

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.Img
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.shared.ClickableCoilImage
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.*
import java.io.File

@ExperimentalCoilApi
@Preview()
@Composable
fun PreviewGridTwo() {
    GridTwo(listOf()) { }
}

@ExperimentalCoilApi
@Composable
fun GridTwo(
    imgs: List<String>,
    onImageClick: (index: Int) -> Unit
) {
    MediaQuery {
        Row(
            modifier = Modifier
                .height(it.div(2))
        ) {

            val imageOneUrl = if(File(imgs[0]).exists()) File(imgs[0]) else imgs[0]
            val imageTwoUrl = if(File(imgs[1]).exists()) File(imgs[1]) else imgs[1]

            val imagePainterOne = rememberImagePainter(data = imageOneUrl)
            val stateOne = imagePainterOne.state

            val imagePainterTwo = rememberImagePainter(data = imageTwoUrl)
            val stateTwo = imagePainterTwo.state


            ClickableCoilImage(
                painter = imagePainterOne,
                placeholder = 0,
                contentDescription = ImageActions.VIEW.contentDescription,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,           // crop the image if it's not a square
                modifier = Modifier
                    .fillMaxHeight()
                    .width(0.dp)
                    .weight(1f)
                    .padding(end = 2.dp).placeholder(
                        visible = stateOne is ImagePainter.State.Loading || stateOne is ImagePainter.State.Empty,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                onImageClick = {
                    onImageClick.invoke(0)
                }
            )

//            ClickableImage(
//                resourceId1,
//                imageActions = ImageActions.VIEW,
//                contentScale = ContentScale.Crop,
//                alignment = androidx.compose.ui.Alignment.Center,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(0.dp)
//                    .weight(1f)
//                    .padding(end = 2.dp)
//            ) {
//                onImageClick(0)
//            }

            ClickableCoilImage(
                painter = imagePainterTwo,
                placeholder = 0,
                contentDescription = ImageActions.VIEW.contentDescription,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,           // crop the image if it's not a square
                modifier = Modifier
                    .fillMaxHeight()
                    .width(0.dp)
                    .weight(1f)
                    .padding(start = 2.dp).placeholder(
                        visible = stateTwo is ImagePainter.State.Loading || stateTwo is ImagePainter.State.Empty,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                onImageClick = {
                    onImageClick.invoke(1)
                }
            )

//            ClickableImage(
//                resourceId2,
//                imageActions = ImageActions.VIEW,
//                contentScale = ContentScale.Crop,
//                alignment = androidx.compose.ui.Alignment.Center,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(0.dp)
//                    .weight(1f)
//                    .padding(start = 2.dp)
//            ) {
//                onImageClick(1)
//            }
        }
    }
}