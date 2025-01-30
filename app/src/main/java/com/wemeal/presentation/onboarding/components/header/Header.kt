package com.wemeal.presentation.onboarding.components.header

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.data.model.user.PlaceDataModel
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.onboarding.components.RoundedBox
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import com.wemeal.presentation.util.montserratBold

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewHeader() {
    Header(
        1, {},
        remember { mutableStateOf(false) },
        null,
        querySearch = remember { mutableStateOf("") },
        count = remember { mutableStateOf(0) },
        viewModel = null
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun Header(
    page: Int,
    onConfirmClick: () -> Unit,
    isSearching: MutableState<Boolean>,
    searchResult: SnapshotStateList<PlaceDataModel>?,
    querySearch: MutableState<String>,
    count: MutableState<Int>,
    viewModel: OnBoardingViewModel?
) {

    val context = LocalContext.current

    //====================temp solution===================
    when (page) {
        2 -> count.value = checkFollowedBrands(viewModel)
        3 -> count.value = checkFollowedFoodies(viewModel)
    }
    //====================temp solution===================

    val keyboardController = LocalSoftwareKeyboardController.current
    val isEnabled = if (page > 1) count.value >= 3 else viewModel?.locationModel?.value != null

    val btnText =
        when (page) {
            2, 3 -> stringResource(id = com.wemeal.R.string.next, "Confirm")
            else -> stringResource(id = com.wemeal.R.string.confirm, "NEXT")
        }

    val event = when (page) {
        1 -> CustomEvent.USER_CLICK_CONFIRM_LOCATION
        2 -> CustomEvent.USER_PROCEED_TO_FOLLOW_FOODIE_FROM_CARD_ONBOARDING
        else -> CustomEvent.USER_PROCEED_TO_FEED_PAGE_FROM_ONBOARDING
    }

    Box(
        Modifier
            .fillMaxWidth()
            .height(132.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        blue200,
                        purple400
                    )
                ),
                shape = RoundedCornerShape(
                    topStart = 0.dp, topEnd = 0.dp,
                    bottomStart = 9.dp, bottomEnd = 9.dp
                )
            )
            .padding(top = 11.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                RoundedBox(opacity = 1.0f)
                RoundedBox(opacity = if (page == 1) 0.5f else 1.0f)
                RoundedBox(opacity = if (page == 3) 1.0f else 0.5f)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top
                ) {
                    ClickableText(
                        text = btnText,
                        color = Color.White.copy(alpha = if (isEnabled) 1.0f else 0.5f),
                        modifier = Modifier,
                        style =
                        TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(montserratBold),
                            letterSpacing = TextUnit(-0.56f, TextUnitType.Sp)
                        )
                    ) {
                        if (isEnabled)
                            onConfirmClick.invoke()
                        keyboardController?.hide()

                        context.activity()?.logEvent(
                            event = event, EventCase.ATTEMPT
                        )
                    }
                }
            }
            AnimatedVisibility(visible = page == 1) {
                HeaderOneContent(
                    searchResult,
                    isSearching,
                    querySearch
                )
            }
            AnimatedVisibility(visible = page == 2) { HeaderTwoContent(count) }
            AnimatedVisibility(visible = page == 3) { HeaderThreeContent(count) }
        }
    }
}

private fun checkFollowedFoodies(viewModel: OnBoardingViewModel?): Int {
    var count = 0
    for (foodie in viewModel?.foodiesList?.value!!) {
        if (foodie.isFollowing == true)
            count++
    }
    return count
}

private fun checkFollowedBrands(viewModel: OnBoardingViewModel?): Int {
    var count = 0
    for (foodie in viewModel?.brandsList?.value!!) {
        if (foodie.isFollowing == true)
            count++
    }
    return count
}