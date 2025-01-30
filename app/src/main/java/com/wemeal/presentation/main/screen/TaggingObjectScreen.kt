package com.wemeal.presentation.main.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Indicator
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import com.wemeal.R
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.components.createpost.tagged.TaggedBrand
import com.wemeal.presentation.main.components.createpost.tagged.TaggedMeal
import com.wemeal.presentation.main.components.createpost.tagged.TaggedOffer
import com.wemeal.presentation.main.components.createpost.tagged.TaggedOrderItem
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.onboarding.components.search.EmptyStateView
import com.wemeal.presentation.onboarding.components.text.InputEditText
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewTaggingObjectScreen() {
    TaggingObjectScreen(viewModel = null) {}
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalUnitApi
@Composable
fun TaggingObjectScreen(
    viewModel: MainViewModel?,
    onItemSelected: () -> Unit
) {
    var context = LocalContext.current

    val tabIndex = remember { mutableStateOf(0) } // 1.
    var searchText by rememberSaveable { mutableStateOf("") }
    var typeing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val brandsList = viewModel?.brandsList
    val offersList = viewModel?.offersList
    val mealsList = viewModel?.mealsList
    val ordersList = viewModel?.ordersList

    var size by rememberSaveable { mutableStateOf(0) }
    size = when (tabIndex.value) {
        0 -> brandsList?.value?.size ?: 0
        1 -> mealsList?.value?.size ?: 0
        2 -> offersList?.value?.size ?: 0
        else -> ordersList?.value?.size ?: 0
    }

    val screenEventName =
        when (tabIndex.value) {
            0 -> CustomEvent.USER_CLICK_RESTAURANTS_TAB_TAGGING_RESTAURANT_CREATE_POST
            1 -> CustomEvent.USER_CLICK_MEALS_TAB_TAGGING_MEAL_CREATE_POST
            2 -> CustomEvent.USER_CLICKS_OFFERS_TAB_TAGGING_OFFER_CREATE_POST
            else -> CustomEvent.USER_CLICKS_ORDERS_TAB_TAGGING_MEAL_CREATE_POST
        }

    val searchEventName =
        when (tabIndex.value) {
            0 -> CustomEvent.USER_SEARCH_TAGGING_RESTAURANT_CREATE_POST
            1 -> CustomEvent.USER_SEARCH_TAGGED_MEAL_CREATE_POST
            2 -> CustomEvent.USER_SEARCH_TAGGED_OFFER_CREATE_POST
            else -> CustomEvent.USER_SEARCH_TAGGED_ORDER_CREATE_POST
        }

    val confirmSearchEventName =
        when (tabIndex.value) {
            0 -> CustomEvent.USER_CONFIRM_SEARCH_TAGGING_RESTAURANT_CREATE_POST
            1 -> CustomEvent.USER_CONFIRM_SEARCH_TAGGING_MEAL_CREATE_POST
            2 -> CustomEvent.USER_CONFIRM_SEARCH_TAGGING_OFFER_CREATE_POST
            else -> CustomEvent.USER_CONFIRM_SEARCH_TAGGING_ORDER_CREATE_POST
        }

    //
    val removeSearchEventName =
        when (tabIndex.value) {
            0 -> CustomEvent.USER_REMOVE_SEARCH_QUERY_TAGGING_RESTAURANT
            1 -> CustomEvent.USER_REMOVE_SEARCH_QUERY_TAGGING_MEAL
            2 -> CustomEvent.USER_REMOVE_SEARCH_QUERY_TAGGING_OFFER
            else -> CustomEvent.USER_REMOVE_SEARCH_QUERY_TAGGING_ORDER
        }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(tabIndex.value) {
        searchText = ""
        typeing = false
        viewModel?.clearTaggedList()
        viewModel?.sendCreatePostLog(screenEventName)
    }

    LaunchedEffect(searchText) {
        when {
            searchText.isNotEmpty() -> {
                when (tabIndex.value) {
                    0 -> viewModel?.searchBrands(searchText)
                    1 -> viewModel?.searchMeals(searchText)
                    2 -> viewModel?.searchOffers(searchText)
                    else -> viewModel?.searchOrders(searchText)
                }
                viewModel?.sendCreatePostLog(searchEventName)
            }
            else -> {
                typeing = false
                size = 0
            }
        }
    }

    val searchHint =
        when (tabIndex.value) {
            0 -> stringResource(id = R.string.search_restaurants)
            1 -> stringResource(id = R.string.search_restaurants)
            2 -> stringResource(id = R.string.search_restaurants)
            else -> stringResource(id = R.string.search_restaurants)
        }

    val titleHint =
        when (tabIndex.value) {
            0 -> stringResource(id = R.string.search_restaurants_hint)
            1 -> stringResource(id = R.string.search_meals_hint)
            2 -> stringResource(id = R.string.search_offers_hint)
            else -> stringResource(id = R.string.search_order_hint)
        }

    Column(Modifier.fillMaxSize()) {
        Tabs(tabIndex)

        Card(
            elevation = 2.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(38.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 11.7.dp, end = 11.7.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_icon_search),
                    contentDescription = ImageActions.SEARCH.contentDescription,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(17.dp)
                )
                InputEditText(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        typeing = it.isNotEmpty()
                    },
                    placeHolderString = searchHint,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 1,
                    contentTextStyle = TextStyle(
                        fontSize = 16.sp,
                        color = black200,
                        lineHeight = TextUnit(16f, TextUnitType.Sp),
                        letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                        fontFamily = FontFamily(montserratRegular)
                    ),
                    hintTextStyle = TextStyle(
                        color = black200.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        lineHeight = TextUnit(16f, TextUnitType.Sp),
                        letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                        fontFamily = FontFamily(montserratRegular)
                    ),
                    cursorColor = purple600,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel?.sendCreatePostLog(confirmSearchEventName)
                        })
                )
                AnimatedVisibility(visible = typeing) {
                    ClickableImage(
                        drawableId = R.drawable.ic_close_grey,
                        imageActions = ImageActions.CLOSE,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    ) {
                        searchText = ""
                        typeing = false
                        keyboardController?.hide()
                        viewModel?.clearTaggedList()
                        viewModel?.sendCreatePostLog(removeSearchEventName)
                    }
                }
            }
        }

        AnimatedVisibility(visible = viewModel?.noSearchResults?.value == true && typeing) {
            EmptyStateView(searchText)
        }

        AnimatedVisibility(visible = size == 0 && !typeing) {
            Image(
                painterResource(id = R.mipmap.ic_tag_search),
                contentDescription = ImageActions.SEARCH.contentDescription,
                modifier = Modifier
                    .height(305.dp)
                    .fillMaxWidth()
                    .padding(top = 59.dp, start = 34.dp, end = 34.dp, bottom = 30.dp)
            )
        }

        AnimatedVisibility(visible = size == 0 && !typeing) {
            Text(
                text = titleHint,
                color = black,
                fontSize = 16.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = FontFamily(montserratBold)
                ),
                modifier = Modifier.padding(start = 42.dp, end = 42.dp)
            )
        }

        AnimatedVisibility(visible = size > 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(
                    top = 20.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp
                )
            ) {
                items(size) { index ->
                    when (tabIndex.value) { // 6.
                        0 -> TaggedBrand(brand = brandsList?.value?.get(index),
                            onItemClick = {
                                viewModel?.clearTaggedObjects()
                                viewModel?.taggedBrand?.value = brandsList?.value?.get(index)
                                viewModel?.placeBrand?.value = brandsList?.value?.get(index)?.name
                                viewModel?.isTagged?.value = true
                                onItemSelected.invoke()
                                viewModel?.sendCreatePostLog(CustomEvent.USER_SELECT_TAGGED_RESTAURANT_CREATE_POST)
                            })
                        1 -> TaggedMeal(meal = mealsList?.value?.get(index),
                            isSearch = true,
                            onItemClick = {
                                viewModel?.clearTaggedObjects()
                                viewModel?.taggedMeal?.value = mealsList?.value?.get(index)
                                viewModel?.placeBrand?.value =
                                    mealsList?.value?.get(index)?.placeBrand?.name
                                viewModel?.isTagged?.value = true
                                onItemSelected.invoke()
                                viewModel?.sendCreatePostLog(CustomEvent.USER_SELECT_TAGGED_MEAL_CREATE_POST)
                            })
                        2 -> TaggedOffer(offer = offersList?.value?.get(index),
                            isSearch = true,
                            onItemClick = {
                                viewModel?.clearTaggedObjects()
                                viewModel?.taggedOffer?.value = offersList?.value?.get(index)
                                viewModel?.placeBrand?.value =
                                    offersList?.value?.get(index)?.place?.name
                                viewModel?.isTagged?.value = true
                                onItemSelected.invoke()
                                viewModel?.sendCreatePostLog(CustomEvent.USER_SELECT_TAGGED_OFFER_CREATE_POST)
                            })
                        else -> TaggedOrderItem(order = ordersList?.value?.get(index),
                            onItemClick = {
                                viewModel?.clearTaggedObjects()
                                viewModel?.taggedOrder?.value = ordersList?.value?.get(index)
                                viewModel?.placeBrand?.value =
                                    ordersList?.value?.get(index)?.place?.name
                                viewModel?.isTagged?.value = true
                                onItemSelected.invoke()
                                viewModel?.sendCreatePostLog(CustomEvent.USER_SELECT_TAGGED_ORDER_CREATE_POST)
                            })
                    }
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun Tabs(tabIndex: MutableState<Int>) {
    val context = LocalContext.current
    val tabTitles = context.resources.getStringArray(R.array.tabs)

    Box(
        Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(start = 0.dp, end = 0.dp)
    ) { // 2.
        TabRow(
            backgroundColor = White,
            selectedTabIndex = tabIndex.value,
//            divider = {
//                TabRowDefaults.Divider(
//                    Modifier
//                        .height(0.5.dp)
//                        .background(lightGrey)
//                        .wrapContentSize(Alignment.BottomStart)
//                )
//            },
            indicator = { tabPositions ->
                Indicator(
                    Modifier
                        .zIndex(1f)
                        .tabIndicatorOffset(tabPositions[tabIndex.value]),
                    color = purple600,
                    height = 2.dp
                )
            }

        ) { // 3.
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .zIndex(2f),
                    selected = tabIndex.value == index, // 4.
                    onClick = { tabIndex.value = index },
                    text = {
                        Text(
                            text = title,
                            color = black300,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 9.5.sp,
                            fontFamily = if (tabIndex.value == index)
                                FontFamily(montserratBold)
                            else FontFamily(montserratRegular),
                            lineHeight = TextUnit(0.22f, TextUnitType.Sp),
                            letterSpacing = TextUnit(0.27f, TextUnitType.Sp),
                        )
                    }) // 5.
            }
        }
        Spacer(
            modifier = Modifier
                .padding(top = 33.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.TopCenter)
                .gradientBackground(
                    listOf(
                        regularBlue,
                        lightPurple
                    ), angle = 45f,
                    CornerRadius(60f, 60f),
                    alpha = 0.21f
                )
        )
    }
}
