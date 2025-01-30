package com.wemeal.presentation.onboarding.components.bottomsheet

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.libraries.maps.model.LatLng
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.wemeal.presentation.util.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AreasNavHost(
    navController: NavHostController,
    state: LazyListState,
    areaName: MutableState<String>,
    cityId: MutableState<String>,
    areaId: MutableState<String>,
    searchText: MutableState<String>,
    isBackVisible: MutableState<Boolean>,
    viewModel: OnBoardingViewModel,
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    NavHost(navController = navController, startDestination = CITIES) {
        composable(CITIES) {
            isBackVisible.value = false
            areaName.value = ""
            AreasLazyColumn(
                listType = CITIES,
                state = state,
                navController = navController,
                viewModel = viewModel,
                searchText = searchText,
                onClick = {
                    onClick.invoke()
                }
            )
        }
        composable(
            "${AREAS}/{index}/{name}",
            arguments = listOf(navArgument("index") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType })
        )
        { backStackEntry ->
            isBackVisible.value = true
            areaName.value = backStackEntry.arguments?.getString("name") ?: ""
            cityId.value = backStackEntry.arguments?.getString("index") ?: ""

            AreasLazyColumn(
                listType = AREAS,
                state = state,
                navController = navController,
                viewModel = viewModel,
                _id = cityId,
                searchText = searchText,
                onClick = {
                    onClick.invoke()
                },
                onDismiss = { latLng ->
                    viewModel.selectedLocation = latLng
                    onDismiss()
                }
            )
        }
        composable(
            "${SUB_AREAS}/{index}/{name}",
            arguments = listOf(navArgument("index") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            isBackVisible.value = true
            areaName.value = backStackEntry.arguments?.getString("name") ?: ""
            areaId.value = backStackEntry.arguments?.getString("index") ?: ""

            AreasLazyColumn(
                SUB_AREAS, state = state,
                navController = navController,
                viewModel = viewModel,
                searchText = searchText,
                _id = areaName,
                onDismiss = { latLng ->
                    viewModel.selectedLocation = latLng
                    onDismiss()
                }
            )
        }
    }
}
