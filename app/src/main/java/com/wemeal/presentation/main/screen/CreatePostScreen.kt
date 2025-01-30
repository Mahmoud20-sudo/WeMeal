package com.wemeal.presentation.main.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.components.createpost.BottomSheetContent
import com.wemeal.presentation.main.components.createpost.PopupWindow
import com.wemeal.presentation.main.components.createpost.tagged.*
import com.wemeal.presentation.util.*
import com.wemeal.presentation.main.components.navigation.CreatePostTopBar
import com.wemeal.presentation.main.components.posts.PostImage
import com.wemeal.presentation.main.components.posts.SharedPostNameBox
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.shared.*
import com.wemeal.presentation.shared.dialog.ConfirmationDialog
import com.wemeal.presentation.util.events.CustomEvent
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewCreatePostScreen() {
    CreatePostScreen(
        navController = rememberNavController(),
        viewModel = null,
        isDialogShowing = mutableStateOf(false),
        onPostClick = { },
        onAddImageClick = {},
        onTaggObjectClick = {},
        onImageClick = {},
        postText = mutableStateOf(""),
        isMentionText = mutableStateOf(false)
    )
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalUnitApi
@ExperimentalAnimationApi
@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: MainViewModel?,
    isDialogShowing: MutableState<Boolean>,
    onPostClick: (postAnyway: Boolean) -> Unit,
    onAddImageClick: (isGallery: Boolean) -> Unit,
    onTaggObjectClick: () -> Unit,
    onImageClick: () -> Unit,
    postText: MutableState<String>,
    isMentionText: MutableState<Boolean>
) {
    val context = LocalContext.current
    val user = SharedPreferencesManager.instance.user

    val painter = rememberImagePainter(
        data = user?.facebookProfilePicUrl ?: "https://www.example.com/image.jpg",
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)
            transformations(CircleCropTransformation())
        })

    val sharedPreferencesManager = SharedPreferencesManager.instance
    val state = painter.state
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    var showRulesTipsPopup by remember { mutableStateOf(sharedPreferencesManager.isFirstTime) }//SharedPreferencesManager.instance.isFirstTime
    var showTaggedTipsPopup by remember { mutableStateOf(false) }

    val searchQuery = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    var isPlaceHolderRemoved by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val searchObserver = viewModel?.searchUsersLiveData?.observeAsState()

    var isFocused by remember { mutableStateOf(false) }
    var postAnyway by remember { mutableStateOf(false) }

    val isKeyboardOpen by keyboardAsState()

    val itemsList = viewModel?.imagesList

    val screenHeight =
        LocalContext.current.resources.displayMetrics.heightPixels.dp / LocalDensity.current.density

    val backgroundColor =
        if (isMentionText.value || itemsList?.isNotEmpty() == true) Transparent else White


    LaunchedEffect(viewModel?.usersMap?.size){
        Log.e("AA", "AA")
        if(viewModel?.usersMap?.isNotEmpty() == true)
            viewModel?.sendCreatePostLog(CustomEvent.USER_MENTION_FOODIE_IN_POST)
    }

    LaunchedEffect(viewModel?.sharedToBrand?.value) {
        when (viewModel?.sharedToBrand?.value) {
            true -> viewModel.sendCreatePostLog(CustomEvent.USER_CLICK_ALSO_SHARE_ON_RESTAURANT_PAGE_CREATE_POST)
        }
    }

    LaunchedEffect(isKeyboardOpen.name) {
        if (isKeyboardOpen == Keyboard.Closed) {
            viewModel?.isFocused?.postValue(false)
            coroutineScope.launch {
                isFocused = false
            }
        }
    }

//    LaunchedEffect(isBottomSheetExpanded?.value) {
//        if (isBottomSheetExpanded?.value == true)
//            coroutineScope.launch {
//                keyboardController?.hide()
//                isPosting = true
//                bottomState.animateTo(ModalBottomSheetValue.Expanded)
//            }
//    }

    LaunchedEffect(postText.value) {
        val matcher = Pattern.compile("@").matcher(postText.value)
        if (matcher.find() && !postText.value.substring(matcher.end()).startsWith("\n")) {
            searchQuery.value = postText.value.substring(matcher.end()).trim()
            viewModel?.usersList?.clear()
            viewModel?.searchUsers(searchQuery.value)
        }
    }

    if (viewModel?.isInetractionDialogShown?.value == true) {
        InteractionDialog(
            showing = viewModel.isInetractionDialogShown,
            postAnyway = postAnyway,
            onAddImagesClick = {
                viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_CHOOSE_FROM_RESTAURANT_GALLERY_ADDING_IMAGE_PROMPT_SCREEN_TAGGING_OBJECT,)
                viewModel.isInetractionDialogShown.value = false
                onAddImageClick.invoke(true)
            }, onAddFromGalleryClick = {

                viewModel.isInetractionDialogShown.value = false
                onAddImageClick.invoke(false)
            },
            onPostAnywayClick = {
                viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_POST_ANYWAY_ADDING_IMAGE_PROMPT_POPUP_TAGGING_OBJECT)
                viewModel.isInetractionDialogShown.value = false
                onPostClick.invoke(true)
            })
    }

    if (isDialogShowing.value) {
        ConfirmationDialog(
            title = context.getString(R.string.discard_title),
            message = context.getString(R.string.discard_message),
            positiveButtonTitle = context.getString(R.string.discard),
            negativeButtonTitle = context.getString(R.string.cancel),
            onPositiveClick = {
                viewModel?.sendCreatePostLog(CustomEvent.USER_CONFIRM_DISCARD_POST)
                navController.navigateUp()
                viewModel?.imagesList?.clear()
                isDialogShowing.value = false
            }) {
            viewModel?.sendCreatePostLog(CustomEvent.USER_DISCARD_POST_CANCEL)
            isDialogShowing.value = false
        }
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (topBar, rulesTipsPopup, column, toggle, spacerOne, addImageRow, spacerTwo, addTagObjectRow, taggedTipsPopup, bottomSheet) = createRefs()

        Column(modifier = Modifier
            .constrainAs(column) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
            }
            .fillMaxSize()
            .padding(bottom = 150.dp)
            .verticalScroll(scrollState)
            .clickable(
                enabled = true,
                onClickLabel = "",
                onClick = {
                    isFocused = true
                })
        ) {
            Row(
                Modifier
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = (state is ImagePainter.State.Loading)
                    ) {
                        CircularProgressIndicator(color = blue200)
                    }
                    CoilImage(
                        painter = painter,
                        placeholder = R.drawable.ic_profile_placeholder,
                        contentDescription = "USER_IMAGE",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
                AnimatedVisibility(
                    visible = viewModel?.sharedToBrand?.value == false || viewModel?.isTagged?.value == false,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 9.dp, start = 8.dp), content = {
                        Text(
                            text = "${user?.firstName} ${user?.lastName}",
                            fontSize = 12.sp,
                            color = darkGrey,
                            textAlign = TextAlign.Start,
                            fontFamily = FontFamily(montserratSemiBold),
                            letterSpacing = TextUnit(-0.42f, TextUnitType.Sp),
                        )
                    }
                )
                AnimatedVisibility(
                    visible = viewModel?.sharedToBrand?.value == true && viewModel.isTagged.value,
                    enter = slideInVertically(
                        animationSpec = tween(durationMillis = 250)
                    ),
                    exit = slideOutVertically(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 250)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 9.dp, start = 8.dp, end = 8.dp)
                ) {
                    SharedPostNameBox(
                        modifier = Modifier,
                        nameOneText = "${user?.firstName} ${user?.lastName}",
                        nameTwoText = "${viewModel?.placeBrand?.value?.en}",
                        fontSize = 12.sp,
                        font = R.font.montserrat_semibold
                    )
                }
                ClickableText(
                    text = stringResource(id = R.string.rules),
                    color = purple300,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(montserratBold),
                        letterSpacing = TextUnit(-0.42f, TextUnitType.Sp)
                    ),
                    modifier = Modifier.padding(top = 9.dp)
                ) {
                    showRulesTipsPopup = false
                    viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_RULES_DURING_CREATING_POST)

                    //CALL RULES POPUP
                    coroutineScope.launch {
                        keyboardController?.hide()
                        viewModel?.isPosting?.value = false
                        viewModel?.isBottomSheetExpanded?.postValue(true)
                        bottomState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }
            }

            MentionEditText(
                postText = postText,
                usersMap = viewModel?.usersMap,
                isMentionText = isMentionText,
                isFocused = isFocused,
                searchQuery = searchQuery.value,
                scrollState = scrollState,
                usersList = viewModel?.usersList,
                loading = searchObserver?.value is Resource.Loading,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
            )

            if (itemsList!!.isNotEmpty()) {
                PostImage(
                    imgs = itemsList,
                    onImageClick = { _, _ ->
                        when (itemsList.size) {
                            1 -> {
                                viewModel?.sendCreatePostLog(CustomEvent.USER_REMOVE_IMAGE_SINGLE_IMAGE_SELECTED_CREATE_POST)
                                viewModel.clearImagesList()

                            }
                            else -> {
                                onImageClick.invoke()
                                viewModel?.sendCreatePostLog(CustomEvent.USER_EDIT_IMAGE_SELECTION_CREATE_POST)
                            }
                        }
                    })
            } else {
                AnimatedVisibility(
                    visible = viewModel.isTagged.value && !isPlaceHolderRemoved, modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .background(paleGrey)
                ) {
                    TaggedImagePlaceHolder(onAddImageClick = {
                        viewModel.isInetractionDialogShown.value = true
                    }, onCloseClick = {
                        isPlaceHolderRemoved = true
                    })
                }
            }

            AnimatedVisibility(
                visible = viewModel.isTagged.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 13.dp, end = 16.dp, start = 16.dp)
            ) {
                when {
                    viewModel.taggedBrand.value != null -> TaggedBrand(brand = viewModel.taggedBrand.value,
                        isSelected = true, onCloseClick = {
                            viewModel?.sendCreatePostLog(CustomEvent.USER_REMOVE_TAGGED_RESTAURANT_CREATE_POST)
                            viewModel.clearTaggedObjects()
                        })
                    viewModel.taggedMeal.value != null -> TaggedMeal(meal = viewModel.taggedMeal.value,
                        isSelected = true, onCloseClick = {
                            viewModel?.sendCreatePostLog(CustomEvent.USER_REMOVE_TAGGED_MEAL_CREATE_POST)
                            viewModel.clearTaggedObjects()
                        })
                    viewModel.taggedOffer.value != null -> TaggedOffer(offer = viewModel.taggedOffer.value,
                        isSelected = true, onCloseClick = {
                            viewModel?.sendCreatePostLog(CustomEvent.USER_REMOVE_TAGGED_OFFER_CREATE_POST)
                            viewModel.clearTaggedObjects()
                        })
                    else ->
                        TaggedOrder(order = viewModel.taggedOrder.value,
                            isSelected = true, onCloseClick = {
                                viewModel?.sendCreatePostLog(CustomEvent.USER_REMOVE_TAGGED_ORDER_CREATE_POST)
                                viewModel.clearTaggedObjects()
                            })
                }
            }
        }

        CreatePostTopBar(modifier = Modifier
            .constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()
            .height(height = 56.dp),
            isEnabled = postText.value.isNotEmpty() || itemsList?.isNotEmpty() == true,
            onBackClick = {
                context.activity()?.onBackPressed()
            },
            onPostClick = {
                postAnyway = true
                onPostClick.invoke(false)
            })
        AnimatedVisibility(visible = showRulesTipsPopup, modifier = Modifier
            .constrainAs(rulesTipsPopup) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 40.dp)
            .height(99.3.dp)) {
            PopupWindow(true) {
                showRulesTipsPopup = false
                showTaggedTipsPopup = true
            }
        }

        AnimatedVisibility(visible = viewModel?.isTagged?.value == true, modifier = Modifier
            .constrainAs(toggle) {
                bottom.linkTo(spacerOne.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = 8.dp)
        ) {
            SwitchDemo(stringResource(id = R.string.also_share), viewModel?.sharedToBrand!!)
        }

        Spacer(
            modifier = Modifier
                .constrainAs(spacerOne) {
                    bottom.linkTo(addImageRow.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = 16.5.dp, end = 16.5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(grey.copy(alpha = 0.3f)),
        )
        ClickableRow(modifier = Modifier
            .constrainAs(addImageRow) {
                bottom.linkTo(spacerTwo.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .background(White)
            .padding(top = 11.5.dp, start = 22.dp, end = 22.dp, bottom = 11.5.dp)
            .fillMaxWidth(),
            onClick = {
                when (viewModel?.isTagged?.value) {
                    true -> {
                        viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_ADD_IMAGES_FROM_ADDING_IMAGE_PROMPT_POPUP_TAGGING_OBJECT)
                        postAnyway = false
                        viewModel.isInetractionDialogShown.value = true
                    }
                    else -> {
                        onAddImageClick.invoke(true)
                        viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_ADD_IMAGES_CREATE_POST)
                    }
                }
            }) {
            Image(
                painterResource(id = R.drawable.ic_photo_library),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 17.dp)
                    .size(20.dp)
            )
            Text(
                text = stringResource(id = R.string.add_images),
                color = darkGrey,
                fontSize = 16.sp,
                letterSpacing = TextUnit(-0.56f, TextUnitType.Sp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratRegular)
                )
            )
        }
        Spacer(
            modifier = Modifier
                .constrainAs(spacerTwo) {
                    bottom.linkTo(addTagObjectRow.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = 16.5.dp, end = 16.5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(grey.copy(alpha = 0.3f)),
        )
        AnimatedVisibility(visible = showTaggedTipsPopup, modifier = Modifier
            .constrainAs(taggedTipsPopup) {
                bottom.linkTo(addTagObjectRow.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .background(backgroundColor)
            .padding(start = 17.dp, end = 0.dp, bottom = 0.dp)
            .height(99.3.dp)) {
            PopupWindow(false) {
                showTaggedTipsPopup = false
            }
        }
        ClickableRow(modifier = Modifier
            .constrainAs(addTagObjectRow) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
            .fillMaxWidth()
            .background(White)
            .wrapContentHeight()
            .padding(top = 13.5.dp, start = 22.dp, end = 22.dp, bottom = 12.dp),
            onClick = {
                showTaggedTipsPopup = false
                sharedPreferencesManager.isFirstTime = false
                onTaggObjectClick.invoke()
                viewModel?.sendCreatePostLog(CustomEvent.USER_CLICK_TAG_RESTAURANT_MEAL_OFFER_ORDER_CREATE_POST)
            }) {
            Image(
                painterResource(id = R.drawable.ic_loyalty),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 17.dp)
                    .size(20.dp)
            )
            Text(
                text = stringResource(id = R.string.tag_object),
                color = darkGrey,
                fontSize = 16.sp,
                letterSpacing = TextUnit(-0.56f, TextUnitType.Sp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratRegular)
                )
            )
        }
        AnimatedVisibility(visible = (bottomState.currentValue == ModalBottomSheetValue.Expanded)) {
            Spacer(modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize()
                .background(lightBlack.copy(alpha = 0.3f)))
        }
    }
}

@ExperimentalUnitApi
@Composable
private fun TaggedImagePlaceHolder(
    onAddImageClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable(onClick = { //handle onClick
                onAddImageClick.invoke()
            })
            .fillMaxSize()
            .padding(end = 5.dp, top = 2.dp)
    ) {
        val (image, closeImg, titleText) = createRefs()

        Image(
            painterResource(id = R.drawable.ic_photo_library),
            contentDescription = "VIEW",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(46.dp)
                .height(40.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
        ClickableImage(
            drawableId = R.mipmap.ic_clear_shadowed,
            imageActions = ImageActions.CLOSE,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .constrainAs(closeImg) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .clip(CircleShape)
                .size(40.dp)
                .padding(5.dp)
        ) {
            onCloseClick.invoke()
        }
        Text(
            text = stringResource(id = R.string.add_images_to_post),
            color = purple300,
            fontSize = 12.6.sp,
            letterSpacing = TextUnit(0.45f, TextUnitType.Sp),
            style = TextStyle(
                fontFamily = FontFamily(montserratMedium)
            ),
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 16.dp)
        )
    }
}

//
//private fun handleMentionCases(it: String): Boolean {
//    return when {
//        it.isNotEmpty() && (
//                it[it.length - 1] == '@') -> {
//            Log.e("AAA", "AAA")
//            true
//        }
//        else -> false
//    }
//}
//
//class ColorsTransformation : VisualTransformation {
//    override fun filter(text: AnnotatedString): TransformedText {
//        return TransformedText(
//            buildAnnotatedStringWithColors(text.toString()),
//            OffsetMapping.Identity
//        )
//    }
//}
//
//fun buildAnnotatedStringWithColors(text: String): AnnotatedString {
//    val words: List<String> = text.split("\\s+".toRegex())// splits by whitespace
//
//    val colors = listOf(black300, purple600)
//
//    val builder = AnnotatedString.Builder()
//    for ((count, word) in words.withIndex()) {
//
//        builder.withStyle(
//            style = SpanStyle(
//                color = if (word.startsWith("@")) colors[1] else colors[0],
//                fontFamily = FontFamily(if (word.startsWith("@")) montserratMedium else montserratRegular),
//                fontSize = 12.sp
//            )
//        ) {
//            append(word)
//        }
//    }
//    onClick.invoke()
//    val notes = "Eman Mohamed"
//    val sb = SpannableStringBuilder(text)
//    val p: Pattern = Pattern.compile("Eman Mohamed", Pattern.CASE_INSENSITIVE)
//    val m: Matcher = p.matcher(text)
//    while (m.find()) {
//        //String word = m.group();
//        //String word1 = notes.substring(m.start(), m.end());
//        sb.setSpan(
//            ForegroundColorSpan(android.graphics.Color.BLUE),
//            m.start(),
//            m.end(),
//            Spannable.SPAN_INCLUSIVE_INCLUSIVE
//        )
//    }

//    return builder.toAnnotatedString()
//}

