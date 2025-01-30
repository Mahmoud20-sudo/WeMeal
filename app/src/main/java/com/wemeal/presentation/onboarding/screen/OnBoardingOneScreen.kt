package com.wemeal.presentation.onboarding.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.user.PlaceDataModel
import com.wemeal.presentation.BaseViewModel
import com.wemeal.presentation.WeMealApp
import com.wemeal.presentation.onboarding.components.BottomSheet
import com.wemeal.presentation.onboarding.components.bottomsheet.AreasSheetContent
import com.wemeal.presentation.shared.BottomSheetLayout
import com.wemeal.presentation.onboarding.components.header.Header
import com.wemeal.presentation.onboarding.components.map.GoogleMapSnapshot
import com.wemeal.presentation.onboarding.components.search.SearchView
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.shared.LoadingView
import kotlinx.coroutines.launch

//var selectedLocation by mutableStateOf(LatLng(31.2088705, 29.9070125))

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewOnBoardingOneScreen() {
    OnBoardingOneScreen(
        loadingDetails = mutableStateOf(false),
        page = 1,
        viewModel = BaseViewModel(WeMealApp()) as OnBoardingViewModel
    ) { }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalUnitApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnBoardingOneScreen(
    page: Int,
    viewModel: OnBoardingViewModel,
    loadingDetails: MutableState<Boolean>,
    onClick: () -> Unit
) {
    val isLoading: MutableState<Boolean> = remember { mutableStateOf(true) }
    val searchResult = remember { mutableStateListOf<PlaceDataModel>() }
    val querySearch = remember { mutableStateOf("") }
    val anim = rememberLottieAnimatable()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_loading))
    LaunchedEffect(composition) {
        anim.animate(
            composition,
            iterations = LottieConstants.IterateForever,
        )
    }

    val isSearching = remember { mutableStateOf(false) }
    val zoomLevel = remember { mutableStateOf(15f) }
    val count = remember { mutableStateOf(0) }
    val currentlySelectedJobId = remember { mutableStateOf(0) }

    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (mapView, header, bottomSheet, loadingView) = createRefs()

        viewModel.selectedLocation?.let {
            GoogleMapSnapshot(
                isLoading = isLoading,
                location = it,
                zoom = zoomLevel,
                viewModel = viewModel,
                modifier = Modifier
                    .constrainAs(mapView) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                currentlySelectedJobId = currentlySelectedJobId.value
            )
        }
        Column(modifier = Modifier
            .constrainAs(header) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
        ) {
            Header(
                page = page,
                onConfirmClick = onClick,
                isSearching = isSearching,
                searchResult = searchResult,
                querySearch = querySearch,
                count = count,
                viewModel = viewModel
            )
            AnimatedVisibility(
                visible = isSearching.value, modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                SearchView(
                    searchResult = searchResult,
                    querySearch = querySearch,
                    zoomLevel = zoomLevel,
                    loadingDetails = loadingDetails,
                    viewModel = viewModel,
                    isSearching = isSearching
                )
            }
        }
        AnimatedVisibility(
            visible = !isSearching.value,
            modifier = Modifier
                .constrainAs(bottomSheet) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            BottomSheet(
                isLoading.value,
                modifier = Modifier
                    .fillMaxWidth(),
                viewModel = viewModel
            ) {
                coroutineScope.launch {
                    bottomState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        }
        BottomSheetLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp),
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            bottomState = bottomState
        ) {
            AreasSheetContent(
                bottomState = bottomState,
                viewModel = viewModel,
                coroutineScope = coroutineScope,
                currentlySelectedJobId = currentlySelectedJobId
            )
        }
        LoadingView(
            isLoading = loadingDetails.value,
            modifier = Modifier
                .constrainAs(loadingView) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize()
        )
    }
}