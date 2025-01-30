package com.wemeal.data.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.wemeal.R
import com.wemeal.presentation.util.*

sealed class Screen(
    val route: String,
    @DrawableRes val selectedResourceId: Int,
    @DrawableRes val resourceId: Int,
    var selected : MutableState<Boolean>
) {
    object Home : Screen(HOME, R.drawable.ic_home_circle, R.drawable.ic_home, mutableStateOf(true))
    object Restaurants : Screen(RESTAURANTS, R.drawable.ic_restaurants_circle, R.drawable.ic_restaurants, mutableStateOf(false))
    object Offers : Screen(OFFERS, R.drawable.ic_offers_circle, R.drawable.ic_offers, mutableStateOf(false))
    object Foodies : Screen(FOODIES, R.drawable.ic_foodies_circle, R.drawable.ic_foodies, mutableStateOf(false))
    object Profile : Screen(PROFILE, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object Details : Screen(DETAILS, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object Pager : Screen(IMAGES_PAGER, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object Report : Screen(REPORT, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object MyAccount : Screen(MY_ACCOUNT, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object FollowedFoodies : Screen(FOLLOWED_FOODIES, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object ALLFoodies : Screen(ALL_FOODIES, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object ALLRestaurants : Screen(ALL_RESTAURANT, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object CreatePost : Screen(CREATE_POST, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object ImagesEdit : Screen(IMAGES_EDIT, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object TagObject : Screen(TAG_OBJECT, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))
    object Gallery : Screen(GALLERY, R.drawable.ic_profile_circle, R.drawable.ic_profile, mutableStateOf(false))

}