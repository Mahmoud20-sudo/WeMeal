package com.wemeal.presentation.onboarding.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.android.libraries.maps.model.LatLng
import com.wemeal.R
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.onboarding.components.text.LocationText
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import com.wemeal.presentation.util.montserratBold
import com.wemeal.presentation.util.montserratMedium

@SuppressLint("UnrememberedMutableState")
@ExperimentalUnitApi
@Preview
@Composable
fun BottomSheetPreview() {
    BottomSheet(
        isLoading = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        viewModel = null
    )
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalUnitApi
@Composable
fun BottomSheet(
    isLoading: Boolean,
//    selectedLocation: LatLng,
    viewModel: OnBoardingViewModel?,
    modifier: Modifier,
    onChangeClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val locationModel = viewModel?.locationModel
    val areaName = "${context.getString(R.string.suggested_areas)} ${viewModel?.locationModel?.value?.result?.get(0)?.name?.en ?: ""}"

    Card(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        backgroundColor = Color.White,
        modifier = modifier.height(if (locationModel?.value?.result?.size ?: 0 > 1) 165.dp else 80.dp)
    )
    {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.ur_current_location_label, ""),
                    color = darkGreyColor,
                    fontSize = 11.7.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratMedium)
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )
                LocationText(isLoading, viewModel = viewModel)
                AnimatedVisibility(
                    visible = locationModel?.value?.result?.size ?: 0 > 1,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 14.dp)
                        .background(color = whitePurple)
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Column {
                        Text(
                            text = areaName,
                            color = darkBlack,
                            letterSpacing = TextUnit(0f, TextUnitType.Sp),
                            fontSize = 12.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(montserratMedium)
                            ),
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        LazyRow(contentPadding = PaddingValues(start = 16.dp, end = 8.dp)) {
                            items(
                                locationModel?.value?.result!!.subList(
                                    1,
                                    locationModel.value?.result!!.size
                                )
                            ) { area ->
                                RoundedText(area) { selected ->
                                    viewModel.selectedLocation =
                                        selected.location?.coordinates?.let {
                                            LatLng(
                                                it[1],
                                                it[0]
                                            )
                                        }
                                }
                            }
                        }
                    }
                }
            }
            ClickableText(
                text = stringResource(id = R.string.change), color = purple300, modifier =
                Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(end = 16.dp, top = 16.dp), style =
                TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratBold),
                    letterSpacing = TextUnit(-0.49f, TextUnitType.Sp)
                )
            ) {
                onChangeClick()
                context.activity()?.logEvent(
                    CustomEvent.USER_CLICK_CHANGE_LOCATION, EventCase.ATTEMPT
                )
            }
        }
    }
}