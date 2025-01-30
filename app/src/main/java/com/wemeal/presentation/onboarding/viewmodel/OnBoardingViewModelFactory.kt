package com.wemeal.presentation.onboarding.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wemeal.domain.usecase.auth.PatchUserUseCase
import com.wemeal.domain.usecase.onboarding.*

class OnBoardingViewModelFactory(
    private val app: Application,
    private val getNearestAreasUseCase: GetNearestAreasUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getAreasUseCase: GetAreasUseCase,
    private val getSubAreasUseCase: GetSubAreasUseCase,
    private val confirmAreaUseCase: ConfirmAreaUseCase,
    private val getSuggestedFoodiesUseCase: GetSuggestedFoodiesUseCase,
    private val getSuggestedBrandsUseCase: GetSuggestedBrandsUseCase,
    private val followFoodieUseCase: FollowFoodieUseCase,
    private val unFollowFoodieUseCase: UnFollowFoodieUseCase,
    private val followBrandUseCase: FollowBrandUseCase,
    private val unFollowBrandUseCase: UnFollowBrandUseCase,
    private val patchUserUseCase: PatchUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnBoardingViewModel::class.java)) {
            return OnBoardingViewModel(
                app = app,
                getNearestAreasUseCase = getNearestAreasUseCase,
                getCountriesUseCase = getCountriesUseCase,
                getCitiesUseCase = getCitiesUseCase,
                getAreasUseCase = getAreasUseCase,
                getSubAreasUseCase = getSubAreasUseCase,
                getSuggestedFoodiesUseCase = getSuggestedFoodiesUseCase,
                getSuggestedBrandsUseCase = getSuggestedBrandsUseCase,
                followFoodieUseCase = followFoodieUseCase,
                unFollowFoodieUseCase = unFollowFoodieUseCase,
                followBrandsUseCase = followBrandUseCase,
                unFollowBrandUseCase = unFollowBrandUseCase,
                confirmAreaUseCase = confirmAreaUseCase,
                patchUserUseCase = patchUserUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown view model")
    }
}