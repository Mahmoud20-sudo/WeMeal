package com.wemeal.presentation.main.components.grids

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.Img
import com.wemeal.presentation.intro.dimmedColor200
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.shared.ClickableCoilImage
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.*
import java.io.File

@ExperimentalCoilApi
@Preview()
@Composable
fun PreviewGridThree() {
    GridThree(
        listOf()
    ) { }
}

@ExperimentalCoilApi
@Composable
fun GridThree(
    imgsList: List<String>,
    onImageClick: (imgIndex: Int) -> Unit
) {
    MediaQuery {
        val size = remember { mutableStateOf(imgsList.size) }
        val imageOneUrl = if (File(imgsList[0]).exists()) File(imgsList[0]) else imgsList[0]
        val imageTwoUrl = if (File(imgsList[1]).exists()) File(imgsList[1]) else imgsList[1]
        val imageThreeUrl = if (File(imgsList[2]).exists()) File(imgsList[2]) else imgsList[2]


        val imagePainterOne = rememberImagePainter(data = imageOneUrl)
        val stateOne = imagePainterOne.state

        val imagePainterTwo = rememberImagePainter(data = imageTwoUrl)
        val stateTwo = imagePainterTwo.state

        val imagePainterThree = rememberImagePainter(data = imageThreeUrl)
        val stateThree = imagePainterThree.state

        ConstraintLayout {
            val (imageOne, column) = createRefs()

            ClickableCoilImage(
                painter = imagePainterOne,
                placeholder = 0,
                contentDescription = ImageActions.VIEW.contentDescription,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,           // crop the image if it's not a square
                modifier = Modifier
                    .width(it.div(2))
                    .height(it.div(2))
                    .constrainAs(imageOne) {
                        start.linkTo(parent.start)
                    }
                    .padding(end = 2.dp)
                    .padding(end = 2.dp)
                    .placeholder(
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
//                imgsList[0].drawable,
//                contentScale = ContentScale.Crop,
//                imageActions = ImageActions.VIEW,
//                modifier = Modifier
//                    .width(it.div(2))
//                    .height(it.div(2))
//                    .constrainAs(imageOne) {
//                        start.linkTo(parent.start)
//                    }
//                    .padding(end = 2.dp)
//            ) {
//                onImageClick(0)
//            }
            Column(
                modifier = Modifier
                    .width(it.div(2))
                    .height(it.div(2))
                    .constrainAs(column) {
                        start.linkTo(imageOne.end)
                        end.linkTo(parent.end)
                    }
                    .padding(start = 2.dp)
                    .padding(end = 2.dp)
                    .placeholder(
                        visible = stateTwo is ImagePainter.State.Loading || stateTwo is ImagePainter.State.Empty,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    )
            ) {
//                ClickableImage(
//                    imgsList[1].drawable,
//                    imageActions = ImageActions.VIEW,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .weight(1.0f)
//                        .padding(bottom = 2.dp)
//                ) {
//                    onImageClick(1)
//                }

                ClickableCoilImage(
                    painter = imagePainterTwo,
                    placeholder = 0,
                    contentDescription = ImageActions.VIEW.contentDescription,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,           // crop the image if it's not a square
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(it.div(4))
                        .padding(bottom = 2.dp),
                    onImageClick = {
                        onImageClick.invoke(1)
                    }
                )

                ClickableCoilImage(
                    painter = imagePainterThree,
                    placeholder = 0,
                    contentDescription = ImageActions.VIEW.contentDescription,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,           // crop the image if it's not a square
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(it.div(4))
                        .padding(top = 2.dp)
                        .padding(end = 2.dp)
                        .placeholder(
                            visible = stateThree is ImagePainter.State.Loading || stateThree is ImagePainter.State.Empty,
                            color = lightSky,
                            // optional, defaults to RectangleShape
                            shape = RoundedCornerShape(4.dp),
                            highlight = PlaceholderHighlight.fade(
                                highlightColor = Color.White,
                            ),
                        ),
                    onImageClick = {
                        onImageClick.invoke(2)
                    }
                )

//                ClickableImage(
//                    imgsList[2].drawable,
//                    imageActions = ImageActions.VIEW,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .weight(1.0f)
//                        .padding(top = 2.dp)
//                ) {
//                    onImageClick(2)
//                }
            }
        }
    }
}