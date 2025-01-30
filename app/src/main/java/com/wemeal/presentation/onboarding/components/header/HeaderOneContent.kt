package com.wemeal.presentation.onboarding.components.header

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.data.model.user.PlaceDataModel
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewHeaderOneContent() {
    HeaderOneContent(
        null,
        isSearching = remember { mutableStateOf(false) },
        querySearch = remember { mutableStateOf("") }
    )
}

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun HeaderOneContent(
    searchResult: SnapshotStateList<PlaceDataModel>?,
    isSearching: MutableState<Boolean>,
    querySearch: MutableState<String>
) {
//    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
    ) {
        Text(
            text = stringResource(id = R.string.select_ur_area, "Confirm"),
            color = Color.White,
            fontSize = 14.sp,
            letterSpacing = TextUnit(-0.49f, TextUnitType.Sp),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratSemiBold)
            ), modifier = Modifier.padding(top = 16.dp, bottom = 15.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .height(38.dp)
                .padding(start = 14.7.dp, end = 10.7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableImage(
                drawableId = if (!isSearching.value) R.drawable.ic_icon_search else R.drawable.ic_arrow_back,
                imageActions = ImageActions.SEARCH,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(16.7.dp)
                    .height(16.7.dp)
            ) {
                isSearching.value = false
            }
            TextFieldWithDropDown(
                modifier = Modifier
                    .fillMaxWidth(),
                searchResult,
                isSearching,
                querySearch
            )
        }
    }
}