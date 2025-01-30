package com.wemeal.presentation.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.core.os.bundleOf
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.wemeal.R
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.intro.gradientBackground
import com.wemeal.presentation.intro.lightPurple
import com.wemeal.presentation.intro.purple600
import com.wemeal.presentation.intro.regularBlue
import com.wemeal.presentation.main.components.createpost.tagged.InteractionDialog
import com.wemeal.presentation.util.*
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.shared.ClickableCoilImage
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.CustomEventParams
import com.wemeal.presentation.util.events.EventCase
import java.io.File

@ExperimentalCoilApi
@Preview
@Composable
fun PreviewImagesEditScreen() {
    ImagesEditScreen(viewModel = null, onCloseClick = {}, onAddImageClick = {})
}

@ExperimentalCoilApi
@OptIn(ExperimentalUnitApi::class)
@Composable
fun ImagesEditScreen(
    viewModel: MainViewModel?,
    onCloseClick: (url: String) -> Unit,
    onAddImageClick: (isGallery: Boolean) -> Unit
) {
    val context = LocalContext.current

    if (viewModel?.isInetractionDialogShown?.value == true) {
        InteractionDialog(
            showing = viewModel.isInetractionDialogShown,
            onAddImagesClick = {
                viewModel.isInetractionDialogShown.value = false
                onAddImageClick.invoke(true)
            }, onAddFromGalleryClick = {
                viewModel.isInetractionDialogShown.value = false
                onAddImageClick.invoke(false)
            })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel?.imagesList?.size!!) { index ->
                val imageUrl = if (File(viewModel.imagesList.get(index = index)).exists()) File(
                    viewModel.imagesList.get(index = index)
                ) else viewModel.imagesList.get(index = index)

                ClickableCoilImage(
                    painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {
                            size(OriginalSize)
                        }),
                    placeholder = R.drawable.ic_clear,
                    contentDescription = ImageActions.VIEW.contentDescription,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,           // crop the image if it's not a square
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    onCloseClick = {
                        onCloseClick.invoke(viewModel?.imagesList?.get(index = index))
                        context
                            .activity()
                            ?.logEvent(
                                event = CustomEvent.USER_REMOVE_IMAGE_SINGLE_IMAGE_SELECTED_CREATE_POST,
                                EventCase.SUCCESS,
                                bundle = bundleOf(
                                    CustomEventParams.OBJECT_TYPE.name to viewModel?.getObjectType(),
                                    CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel?.isTagged?.value == true) 1 else 0),
                                    CustomEventParams.POST_WITH_TEXT.name to (if (viewModel?.postText?.value?.isNotEmpty() == true) 1 else 0),
                                    CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel?.imagesList?.isNotEmpty() == true) 1 else 0)
                                )
                            )
                    }
                )
            }
//            items(viewModel?.galleryImageList?.size!!) { index ->
//                ClickableCoilImage(
//                    painter = rememberImagePainter(
//                        data = viewModel.galleryImageList.get(index = index),
//                        builder = {
//                            size(OriginalSize)
//                        }),
//                    placeholder = R.drawable.ic_clear,
//                    contentDescription = ImageActions.VIEW.contentDescription,
//                    contentScale = ContentScale.Crop,
//                    alignment = Alignment.Center,           // crop the image if it's not a square
//                    modifier = Modifier
//                        .padding(bottom = 8.dp),
//                    onCloseClick = {
//                        onCloseClick.invoke(viewModel?.galleryImageList?.get(index = index))
//                        context
//                            .activity()
//                            ?.logEvent(
//                                event = CustomEvent.USER_REMOVE_IMAGE_SINGLE_IMAGE_SELECTED_CREATE_POST,
//                                EventCase.SUCCESS
//                            )
//                    }
//                )
//            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .fillMaxWidth()
                .height(67.dp)
                .padding(all = 16.dp)
        ) {
            ClickableText(
                text = stringResource(id = R.string.add_images),
                modifier = Modifier
                    .gradientBackground(
                        listOf(
                            regularBlue,
                            lightPurple
                        ), angle = 45f,
                        CornerRadius(60f, 60f),
                        alpha = 0.3f
                    )
                    .fillMaxSize()
                    .padding(top = 7.dp, bottom = 7.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = purple600,
                    fontFamily = FontFamily(montserratMedium),
                    textAlign = TextAlign.Center
                )
            ) {
                when (viewModel?.isTagged?.value) {
                    true -> {
                        viewModel.isInetractionDialogShown.value = true
                        viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_ADD_IMAGES_FROM_ADDING_IMAGE_PROMPT_SCREEN_TAGGING_OBJECT)
                    }

                    else -> {
                        onAddImageClick.invoke(true)
                        context
                            .activity()
                            ?.logEvent(
                                event = CustomEvent.USER_CLICK_ADD_IMAGES_CREATE_POST,
                                EventCase.SUCCESS,
                                bundle = bundleOf(
                                    CustomEventParams.OBJECT_TYPE.name to viewModel?.getObjectType(),
                                    CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (viewModel?.isTagged?.value == true) 1 else 0),
                                    CustomEventParams.POST_WITH_TEXT.name to (if (viewModel?.postText?.value?.isNotEmpty() == true) 1 else 0),
                                    CustomEventParams.POST_WITH_IMAGE.name to (if (viewModel?.imagesList?.isNotEmpty() == true) 1 else 0)
                                )
                            )
                    }
                }
            }
        }
    }
}