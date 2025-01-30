package com.wemeal.presentation.onboarding.components.bottomsheet

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.intro.darkBlack
import com.wemeal.presentation.onboarding.components.text.InputEditText
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.*
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ThreadLocalRandom

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@OptIn(ExperimentalUnitApi::class)
@ExperimentalMaterialApi
@Composable
fun AreasSheetContent(
    bottomState: ModalBottomSheetState,
    viewModel: OnBoardingViewModel,
    coroutineScope: CoroutineScope,
    currentlySelectedJobId: MutableState<Int>,
) {
    val searchText = rememberSaveable { mutableStateOf("") }
    //searchText = ""//for not saving text in search field when navigating

    val state = rememberLazyListState()
    val navController = rememberNavController()
    val isBackVisible = remember { mutableStateOf(false) }
    val areaName = rememberSaveable { mutableStateOf("") }
    val cityId = rememberSaveable { mutableStateOf("") }
    val areaId = rememberSaveable { mutableStateOf("") }
    var job: Job? = null

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val titleID =
        stringResource(
            id = when {
                navController.currentDestination?.route?.contains(SUB_AREAS) == true ->
                    R.string.select_ur_subarea_in
                navController.currentDestination?.route?.contains(AREAS) == true ->
                    R.string.select_ur_area_in
                else -> R.string.select_ur_city
            }
        )


    val search =
        stringResource(
            id = when {
                navController.currentDestination?.route?.contains(SUB_AREAS) == true ->
                    R.string.search_subareas
                navController.currentDestination?.route?.contains(AREAS) == true ->
                    R.string.search_areas
                else -> R.string.search_cities
            }
        )

    val _id =
        if (navController.currentDestination?.route?.contains(AREAS) == true) cityId else areaName

    val event =
        when {
            navController.currentDestination?.route?.contains(SUB_AREAS) == true ->
                CustomEvent.USER_SEARCH_SUBAREA_FROM_LIST
            navController.currentDestination?.route?.contains(AREAS) == true ->
                CustomEvent.USER_SEARCH_AREA_FROM_LIST
            else ->
                CustomEvent.USER_SEARCH_CITY_FROM_LIST
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp, top = 20.dp, end = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedBack(isBackVisible.value) {
                navController.popBackStack()
                searchText.value = ""
            }
            Text(
                modifier = Modifier
                    .padding(
                        start = if (isBackVisible.value) 10.dp else 0.dp,
                        end = 8.dp
                    )
                    .fillMaxWidth()
                    .weight(1f),
                text = "$titleID ${areaName.value}",
                color = darkBlack,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratBold)
                )
            )
            ClickableImage(
                drawableId = R.drawable.quantum_ic_clear_grey600_24,
                imageActions = ImageActions.CLOSE,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
                    .padding(2.dp)
            ) {
                clearPopupStatus(
                    coroutineScope = coroutineScope,
                    bottomState = bottomState,
                    navController = navController,
                    searchText = searchText,
                    keyboardController = keyboardController
                )
            }
        }
        Surface(
            contentColor = Color(0xFFFFFFFF),
            modifier = Modifier.padding(top = 16.dp),
            shape = CircleShape,
            elevation = 2.dp,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .height(38.dp)
                    .padding(start = 14.7.dp, end = 10.7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableImage(
                    drawableId = R.drawable.ic_icon_search,
                    imageActions = ImageActions.SEARCH,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(16.7.dp)
                        .height(16.7.dp)
                ) {
                    //Search
                }
                InputEditText(
                    value = searchText.value,
                    placeHolderString = "$search ${areaName.value}",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            context
                                .activity()
                                ?.logEvent(
                                    event = event, EventCase.ATTEMPT
                                )//success and fail events when integerating with backend
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 9.7.dp),
                    onValueChange = {
                        searchText.value = it
                        job?.cancel()
                        job = coroutineScope.launch {
                            delay(500)
                            callApi(
                                isSearch = true,
                                viewModel = viewModel,
                                listType = navController.currentDestination?.route.toString(),
                                searchText = searchText.value,
                                _id = _id
                            )
                        }
                    },
                    maxLines = 1,
                    contentTextStyle = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black,
                        letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratRegular)
                    ),
                    hintTextStyle = TextStyle(
                        color = darkBlack.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratRegular)
                    )
                )
            }
        }
        AreasNavHost(
            navController = navController,
            state = state,
            areaName = areaName,
            cityId = cityId,
            areaId = areaId,
            searchText = searchText,
            isBackVisible = isBackVisible,
            viewModel = viewModel,
            onClick = {//no location search results found button click detected
                currentlySelectedJobId.value =
                    ThreadLocalRandom.current().nextInt(RC_LOCATION_NO_RESULT_FOUND, Int.MAX_VALUE)
                clearPopupStatus(
                    coroutineScope = coroutineScope,
                    bottomState = bottomState,
                    navController = navController,
                    searchText = searchText,
                    keyboardController = keyboardController
                )
            },
            onDismiss = {//close
                clearPopupStatus(
                    coroutineScope = coroutineScope,
                    bottomState = bottomState,
                    navController = navController,
                    searchText = searchText,
                    keyboardController = keyboardController
                )
            }
        )
    }
}

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
private fun clearPopupStatus(
    coroutineScope: CoroutineScope,
    bottomState: ModalBottomSheetState,
    navController: NavController,
    searchText: MutableState<String>,
    keyboardController: SoftwareKeyboardController?
) {
    coroutineScope.launch {
        bottomState.hide()
        searchText.value = ""
        keyboardController?.hide()
        delay(200)
        navController.navigate(CITIES)
    }
}

fun callApi(
    viewModel: OnBoardingViewModel,
    listType: String,
    searchText: String,
    _id: MutableState<String> = mutableStateOf(""),
    isSearch: Boolean = false
) {
    when {
        isSearch -> {
            viewModel.resetData(listType = listType)
        }
    }

    when {
        listType.contains(CITIES) -> viewModel.getCities(searchText = searchText)
        listType.contains(SUB_AREAS) -> viewModel.getSubAreas(
            areaId = _id.value,
            searchText = searchText
        )
        else -> viewModel.getAreas(cityID = _id.value, searchText = searchText)
    }

//    when (listType) {
//        CITIES -> viewModel.getCities(searchText = searchText)
//        AREAS -> viewModel.getAreas(cityID = _id.value, searchText = searchText)
//        else -> viewModel.getSubAreas(areaId = _id.value, searchText = searchText)
//    }
}
