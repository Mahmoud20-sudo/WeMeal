package com.wemeal.presentation.main.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.presentation.util.*
import com.wemeal.presentation.main.components.foody.AllFoodyItem

@Preview
@Composable
fun PreviewAllFoodiesScreen() {
    AllFoodiesScreen()
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AllFoodiesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(followedFoodiesList.size) { index ->
            AllFoodyItem(followedFoodiesList[index])
        }
    }
}