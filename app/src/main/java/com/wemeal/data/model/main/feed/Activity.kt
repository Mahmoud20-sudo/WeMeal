package com.wemeal.data.model.main.feed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Ignore
import com.wemeal.data.model.Comment
import com.wemeal.data.model.main.feed.tagged.order.Following
import com.wemeal.data.model.user.UserModel

data class Activity(
    val __v: Int,
    val _id: String,
    val body: String,
    val createdAt: String,
    val follower: Follower,
    val following: Following,
    private val images: List<Images>,
    val mentions: List<UserModel>,
    val updatedAt: String,
    val user: UserModel,
    val taggedOrder: com.wemeal.data.model.main.tagged.orders.Result?,
    val taggedMeal: com.wemeal.data.model.main.tagged.products.Result?,
    val taggedOffer: com.wemeal.data.model.main.tagged.offers.Result?,
    val taggedBrand: com.wemeal.data.model.main.tagged.brands.Result?,
    val sharedToBrand: Boolean?,
    var likesCount: Int?,
    val sharesCount: Int?,
    val commentsCount: Int?,
    val deepLink: String?,
    var isLiked: Boolean?,

    //temp fields
    var isFollowed: MutableState<Boolean> = mutableStateOf(true),
    var comments: List<Comment>? = listOf(),
    var isVerified: Boolean,
    var promotionReason: String = "This restaurant has been ordered many times",
    @Ignore var isUnFollowed: MutableState<Boolean?>? = mutableStateOf(false),
    @Ignore var isUserDeleting: MutableState<Boolean?>? = mutableStateOf(false),
    @Ignore var isPostDeleted: MutableState<Boolean?>? = mutableStateOf(false),
    @Ignore var isConfirmShowing: MutableState<Boolean?>? = mutableStateOf(false),
    @Ignore var isLikedMutable: MutableState<Boolean?>? = mutableStateOf(false),
    @Ignore var likeCountMutable: MutableState<Int?>? = mutableStateOf(0),
    @Ignore var shareCountMutable: MutableState<Int?>? = mutableStateOf(0)
) {
    fun isTagged(): Boolean {
        return taggedOrder != null || taggedMeal != null || taggedOffer != null || taggedBrand != null
    }

    fun getImages(): List<String> {
        val itemsList = mutableListOf<String>()
        if (!images.isNullOrEmpty())
            images.forEach {
                itemsList.add("${it.url}")
            }
        return itemsList
    }
}