package com.wemeal.data.model.main.gallery

data class Result(
    val coverPhoto: String,
    val menuGallery: MutableList<String>,
    val offersGallery: MutableList<String>,
    val restaurantGallery: MutableList<String>,
    val suggestedImages: MutableList<SuggestedImage>
)