package com.wemeal.presentation.main.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.size.OriginalSize
import coil.transform.RoundedCornersTransformation
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.data.model.main.gallery.BrandGalleryModel
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.components.createpost.tagged.TaggedOffer
import com.wemeal.presentation.util.*
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.shared.CoilImage
import com.wemeal.presentation.shared.StaggeredVerticalGrid
import com.wemeal.presentation.shared.dialog.ConfirmationDialog

private val size = mutableStateOf(0)

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@SuppressLint("UnrememberedMutableState")
@ExperimentalFoundationApi
@ExperimentalCoilApi
@Preview
@Composable
fun PreviewOfferGalleryScreen() {
    OfferGalleryScreen(viewModel = null, isDialogShowing = mutableStateOf(false))
}

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@Composable
fun OfferGalleryScreen(
    viewModel: MainViewModel?,
    isDialogShowing: MutableState<Boolean>,
) {
    val context = LocalContext.current

    val coverPainter = rememberImagePainter(
        data = viewModel?.brandGallery?.value?.result?.coverPhoto,
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)
        })

    val suggestedImagePainter = rememberImagePainter(
        data = viewModel?.brandGallery?.value?.result?.suggestedImages?.get(0)?.url,
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)
            transformations(
                RoundedCornersTransformation(
                    topLeft = 20f, topRight = 20f, bottomLeft = 20f, bottomRight = 20f
                )
            )
        })

    val galleryObserver = viewModel?.getGalleryLiveData?.observeAsState()

    LaunchedEffect(viewModel?.added?.value) {
        size.value = viewModel?.imagesList?.size!! + viewModel.galleryImageList.size
        viewModel.added.value = false
    }

    LaunchedEffect(true) {
        viewModel?.getGallery(
            placeId = viewModel.taggedOffer.value?.place?.placeBrand.toString(),
            objectId = viewModel.taggedOffer.value?.id.toString(),
            "taggedOffer"
        )
    }

    if (isDialogShowing.value)
        ConfirmationDialog(
            title = context.getString(R.string.discard_title),
            message = context.getString(R.string.discard_message),
            positiveButtonTitle = context.getString(R.string.discard),
            negativeButtonTitle = context.getString(R.string.cancel),
            onPositiveClick = {
                viewModel?.galleryImageList?.clear()
                context.activity()?.onBackPressed()
                isDialogShowing.value = false
            }) {
            isDialogShowing.value = false
        }

    LazyColumn(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
        item {
            TaggedOffer(
                offer = viewModel?.taggedOffer?.value,
                modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
            )

            Text(
                text = "${size.value} ${stringResource(id = R.string.selected)}",
                modifier = Modifier
                    .padding(top = 7.5.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratRegular),
                    textAlign = TextAlign.End,
                    letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
                )
            )

            Text(
                text = stringResource(id = R.string.suggested_image),
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratBold),
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
                )
            )

            SuggestedImage(
                viewModel = viewModel,
                suggestedImagePainter = suggestedImagePainter,
                galleryObserver = galleryObserver
            )

            Text(
                text = viewModel?.taggedOffer?.value?.title?.en ?: "",
                modifier = Modifier
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratSemiBold),
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.42f, TextUnitType.Sp)
                )
            )

            Text(
                text = stringResource(id = R.string.restaurant_cover),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratBold),
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
                )
            )

            CoverImage(
                viewModel = viewModel,
                coverPainter = coverPainter,
                galleryObserver = galleryObserver
            )

            Text(
                text = viewModel?.taggedOffer?.value?.place?.name?.en ?: "",
                modifier = Modifier
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratSemiBold),
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.42f, TextUnitType.Sp)
                )
            )

            Text(
                text = stringResource(id = R.string.restaurant_gallery),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratBold),
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
                )
            )

            StaggeredVerticalGrid(
                numColumns = 2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                viewModel?.brandGallery?.value?.result?.restaurantGallery?.forEachIndexed { index, item ->
                    val imagePainter = rememberImagePainter(data = item,
                        builder = {
                            memoryCachePolicy(CachePolicy.ENABLED)
                            size(OriginalSize)
                        })
                    DynamicHeightImage(
                        viewModel = viewModel,
                        item = item,
                        imagePainter = imagePainter
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.offers_gallery),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 8.dp, start = 8.dp, end = 16.dp)
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratBold),
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
                )
            )

            StaggeredVerticalGrid(
                numColumns = 2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                viewModel?.brandGallery?.value?.result?.offersGallery?.forEachIndexed { index, item ->
                    val imagePainter = rememberImagePainter(data = item,
                        builder = {
                            memoryCachePolicy(CachePolicy.ENABLED)
                            size(OriginalSize)
                        })
                    DynamicHeightImage(
                        viewModel = viewModel,
                        item = item,
                        imagePainter = imagePainter
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.menu_gallery),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 8.dp, start = 8.dp, end = 16.dp)
                    .placeholder(
                        visible = galleryObserver?.value is Resource.Loading,
                        color = lightSky,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.fade(
                            highlightColor = Color.White,
                        ),
                    ),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(montserratBold),
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
                )
            )

            StaggeredVerticalGrid(
                numColumns = 2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                viewModel?.brandGallery?.value?.result?.menuGallery?.forEachIndexed { index, item ->
                    val imagePainter = rememberImagePainter(data = item,
                        builder = {
                            memoryCachePolicy(CachePolicy.ENABLED)
                            size(OriginalSize)
                        })
                    DynamicHeightImage(
                        viewModel = viewModel,
                        item = item,
                        imagePainter = imagePainter
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun DynamicHeightImage(
    viewModel: MainViewModel?,
    item: String?,
    imagePainter: ImagePainter
) {
    val context = LocalContext.current
    val state = imagePainter.state
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    setupGalleryList(context, viewModel, item)
                })
    ) {
        CoilImage(
            painter = imagePainter,
            contentDescription = "avatar",
            placeholder = R.drawable.ic_cover_placeholder,
            modifier = Modifier
                .padding(
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .placeholder(
                    visible = state is ImagePainter.State.Loading,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
        )
        Spacer(
            modifier = Modifier
                .padding(
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .matchParentSize()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = if (viewModel?.galleryImageList?.contains(item!!) == true) dimmedColor300 else Color.Transparent
                )
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 7.dp, end = 15.dp),
            visible = viewModel?.galleryImageList?.contains(item!!) == true
        ) {
            CounterText(
                modifier = Modifier,
                indexValue = viewModel?.imagesList?.size?.plus(
                    viewModel.galleryImageList.indexOf(
                        item
                    )
                )
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun GalleryImage(
    viewModel: MainViewModel?,
    item: String?,
    imagePainter: ImagePainter
) {
    val context = LocalContext.current
    val state = imagePainter.state
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    setupGalleryList(context, viewModel, item)
                })
    ) {
        CoilImage(
            painter = imagePainter,
            placeholder = R.drawable.ic_cover_placeholder,
            contentDescription = "avatar",
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .fillMaxWidth()
                .height(124.dp)
                .placeholder(
                    visible = state is ImagePainter.State.Loading,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
        )
        Spacer(
            modifier = Modifier
                .padding(
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = if (viewModel?.galleryImageList?.contains(item!!) == true) dimmedColor300 else Color.Transparent
                )
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 7.dp, end = 15.dp),
            visible = viewModel?.galleryImageList?.contains(item!!) == true
        ) {
            CounterText(
                modifier = Modifier,
                indexValue = viewModel?.imagesList?.size?.plus(
                    viewModel.galleryImageList.indexOf(
                        item
                    )
                )
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun CoverImage(
    viewModel: MainViewModel?,
    coverPainter: ImagePainter,
    galleryObserver: State<Resource<BrandGalleryModel>?>?
) {
    val item = viewModel?.brandGallery?.value?.result?.coverPhoto
    val state = coverPainter.state
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .height(95.dp)
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    setupGalleryList(context, viewModel, item)
                })
    ) {
        CoilImage(
            painter = coverPainter,
            placeholder = R.drawable.ic_cover_placeholder,
            contentScale = ContentScale.Crop,
            contentDescription = "avatar",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .placeholder(
                    visible = galleryObserver?.value is Resource.Loading || state is ImagePainter.State.Loading,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = if (viewModel?.galleryImageList?.contains(item) == true) dimmedColor300 else Color.Transparent
                )
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(7.dp),
            visible = viewModel?.galleryImageList?.contains(item) == true
        ) {
            CounterText(
                modifier = Modifier,
                indexValue = viewModel?.imagesList?.size?.plus(
                    viewModel.galleryImageList.indexOf(
                        item
                    )
                )
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun SuggestedImage(
    viewModel: MainViewModel?,
    suggestedImagePainter: ImagePainter,
    galleryObserver: State<Resource<BrandGalleryModel>?>?
) {
    val item = viewModel?.brandGallery?.value?.result?.suggestedImages?.get(0)?.url
    val state = suggestedImagePainter.state
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize()
            .height(130.dp)
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    setupGalleryList(context, viewModel, item)
                })
    ) {
        CoilImage(
            painter = suggestedImagePainter,
            placeholder = R.drawable.ic_cover_placeholder,
            contentDescription = "avatar",
            modifier = Modifier
                .fillMaxSize()
                .placeholder(
                    visible = galleryObserver?.value is Resource.Loading || state is ImagePainter.State.Loading,
                    color = lightSky,
                    // optional, defaults to RectangleShape
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.fade(
                        highlightColor = Color.White,
                    ),
                )
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = if (viewModel?.galleryImageList?.contains(item) == true) dimmedColor300 else Color.Transparent
                )
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(7.dp),
            visible = viewModel?.galleryImageList?.contains(item) == true
        ) {
            CounterText(
                modifier = Modifier,
                indexValue = viewModel?.imagesList?.size?.plus(
                    viewModel.galleryImageList.indexOf(
                        item
                    )
                )
            )
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
private fun CounterText(
    modifier: Modifier,
    indexValue: Int?
) {
    Text(
        text = indexValue?.plus(1).toString(),
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(montserratRegular),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = black,
            letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
        ),
        modifier = modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(Color.White)
    )
}

private fun setupGalleryList(context: Context, viewModel: MainViewModel?, item: String?) {
    if (size.value == 10) {
        context.shortToast(context.getString(R.string.over_limit_msg))
        return
    }
    when {
        viewModel?.galleryImageList?.contains(item) == true -> viewModel.galleryImageList.remove(
            item
        )
        else -> item?.let { viewModel?.galleryImageList?.add(it) }
    }
    viewModel?.added?.value = true
}