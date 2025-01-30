package com.wemeal.presentation.main.components.bottomsheet

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.data.model.MoreModel
import com.wemeal.presentation.intro.darkGrey
import com.wemeal.presentation.util.montserratMedium
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun MoreSheetContent(
    list: List<MoreModel>,
    onItemSelected: (index: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 19.dp, top = 18.5.dp, end = 19.dp, bottom = 18.5.dp)
    ) {
        itemsIndexed(list) { index, item ->
            Row(
                Modifier
                    .clickable(onClick = { //handle onClick
                        onItemSelected(index)
                    })
                    .fillMaxWidth()
                    .padding(bottom = 18.5.dp, top = 18.5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(
                        id = item.drawableId
                    ), contentDescription = "",
                    modifier = Modifier
                        .padding(end = 33.dp)
                        .size(22.dp)
                )
                Text(
                    text = item.title,
                    color = darkGrey,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratMedium)
                    )
                )
            }
        }
    }
}
