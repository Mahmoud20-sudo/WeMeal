package com.wemeal.presentation.main.components.grids

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
import com.wemeal.presentation.intro.lightGray
import com.wemeal.presentation.intro.lightSky
import com.wemeal.presentation.shared.ClickableCoilImage
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.*
import org.w3c.dom.Text
import java.io.File

@Preview()
@Composable
fun PreviewGridFour() {
    GridFour(listOf()) {

    }
}

@ExperimentalCoilApi
@Composable
fun GridFour(imgsList: List<String>, onImageClick: (imgIndex: Int) -> Unit) {
    MediaQuery {

        val size = imgsList.size

        val imageOneUrl = if (File(imgsList[0]).exists()) File(imgsList[0]) else imgsList[0]
        val imageTwoUrl = if (File(imgsList[1]).exists()) File(imgsList[1]) else imgsList[1]
        val imageThreeUrl = if (File(imgsList[2]).exists()) File(imgsList[2]) else imgsList[2]
        val imageFourUrl = if (File(imgsList[3]).exists()) File(imgsList[3]) else imgsList[3]

        val imagePainterOne = rememberImagePainter(data = imageOneUrl)
        val stateOne = imagePainterOne.state

        val imagePainterTwo = rememberImagePainter(data = imageTwoUrl)
        val stateTwo = imagePainterTwo.state

        val imagePainterThree = rememberImagePainter(data = imageThreeUrl)
        val stateThree = imagePainterThree.state

        val imagePainterFour = rememberImagePainter(data = imageFourUrl)
        val stateFour = imagePainterFour.state

        ConstraintLayout {
            val (imageOne, imageTwo, imageThree, imageFour) = createRefs()
//            ClickableImage(
//                imgsList[0].drawable,
//                contentScale = ContentScale.Crop,
//                imageActions = ImageActions.VIEW,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(it.div(2))
//                    .constrainAs(imageOne) {
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//                    .padding(bottom = 4.dp)
//            ){
//                onImageClick(0)
//            }
            ClickableCoilImage(
                painter = imagePainterOne,
                placeholder = 0,
                contentDescription = ImageActions.VIEW.contentDescription,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,           // crop the image if it's not a square
                modifier = Modifier
                    .fillMaxWidth()
                    .height(it.div(2))
                    .constrainAs(imageOne) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(bottom = 4.dp)
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

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(it.div(4))
                    .constrainAs(imageTwo) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(imageOne.bottom)
                    }) {

                ClickableCoilImage(
                    painter = imagePainterTwo,
                    placeholder = 0,
                    contentDescription = ImageActions.VIEW.contentDescription,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,           // crop the image if it's not a square
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(end = 2.dp)
                        .placeholder(
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


//                ClickableImage(
//                   imgsList[1].drawable,
//                    imageActions = ImageActions.VIEW,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .weight(1.0f)
//                        .padding(end = 2.dp)
//                ){
//                    onImageClick(1)
//                }

                ClickableCoilImage(
                    painter = imagePainterThree,
                    placeholder = 0,
                    contentDescription = ImageActions.VIEW.contentDescription,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,           // crop the image if it's not a square
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 2.dp, end = 2.dp)
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
//                   imgsList[2].drawable,
//                    imageActions = ImageActions.VIEW,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .weight(1.0f)
//                        .padding(start = 2.dp, end = 2.dp)
//                ){
//                    onImageClick(2)
//                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 2.dp),
                    contentAlignment = Alignment.Center
                ) {

                    ClickableCoilImage(
                        painter = imagePainterFour,
                        placeholder = 0,
                        contentDescription = ImageActions.VIEW.contentDescription,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,           // crop the image if it's not a square
                        modifier = Modifier
                            .fillMaxSize()
                            .placeholder(
                                visible = stateFour is ImagePainter.State.Loading || stateFour is ImagePainter.State.Empty,
                                color = lightSky,
                                // optional, defaults to RectangleShape
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = Color.White,
                                ),
                            ),
                        onImageClick = {
                            onImageClick.invoke(3)
                        }
                    )

//                    ClickableImage(
//                        imgsList[3].drawable,
//                        imageActions = ImageActions.VIEW,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                    ){
//                        onImageClick(3)
//                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (size > 4) dimmedColor200 else Color.Transparent)
                    )
                    Text(
                        text = "+${size - 4}",
                        color = if (size > 4) Color.White else Color.Transparent,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}