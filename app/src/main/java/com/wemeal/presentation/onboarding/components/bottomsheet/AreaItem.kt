package com.wemeal.presentation.onboarding.components.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.R
import com.wemeal.data.model.onboarding.countries.Result
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratRegular

@ExperimentalUnitApi
@Preview
@Composable
fun PreviewAreaItem() {
    AreaItem(Modifier.fillMaxWidth(), null)
}

@ExperimentalUnitApi
@Composable
fun AreaItem(modifier: Modifier, result: Result?) {

    val name = when {
        !result?.name?.en.isNullOrEmpty() -> result?.name?.en
        !result?.areaOnlyName?.en.isNullOrEmpty() -> result?.areaOnlyName?.en
        else -> result?.subAreaOnlyName?.en
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = black,
            fontSize = 16.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratRegular)
            ),
            modifier = Modifier
                .padding(end = 20.dp)
                .weight(1f)
        )
        Image(
            modifier = Modifier
                .width(12.dp)
                .height(12.dp),
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = "Search",
            contentScale = ContentScale.FillBounds
        )
    }

}