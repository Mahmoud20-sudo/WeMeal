package com.wemeal.presentation.onboarding.components.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.data.model.user.PlaceDataModel
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalUnitApi
@Preview
@Composable
fun PreviewSearchResultItem() {
    SearchResultItem(
        modifier = Modifier.fillMaxSize(), null
    )
}

@ExperimentalUnitApi
@Composable
fun SearchResultItem(
    modifier: Modifier,
    placeDataModel: PlaceDataModel?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.5.dp, start = 16.dp, end = 16.dp, bottom = 8.5.dp)
    ) {
        Box(
            modifier = Modifier
                .gradientBackground(
                    listOf(
                        regularBlue,
                        lightPurple
                    ), angle = 45f,
                    CornerRadius(40f, 40f),
                    alpha = 0.1f
                )
                .width(32.dp)
                .height(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 6.dp, start = 9.dp, end = 9.dp, bottom = 6.dp),
                painter = painterResource(id = R.drawable.ic_map_location),
                contentDescription = "Search",
                contentScale = ContentScale.FillBounds
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = placeDataModel!!.name,
                maxLines = 1,
                color = black,
                fontSize = 14.sp,
                fontFamily = FontFamily(montserratSemiBold)
            )
            Text(
                modifier = Modifier.padding(top = 1.dp),
                text = placeDataModel.description,
                maxLines = 1,
                color = black.copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontFamily = FontFamily(montserratMedium)
            )
            Box(
                modifier = Modifier
                    .padding(top = 6.5.dp)
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(grey),
            )
        }
    }

}