package com.wemeal.presentation.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wemeal.domain.usecase.feed.*
import com.wemeal.domain.usecase.gallery.GetBrandGalleryUseCase
import com.wemeal.domain.usecase.tag.SearchBrandsUseCase
import com.wemeal.domain.usecase.tag.SearchMealsUseCase
import com.wemeal.domain.usecase.tag.SearchOffersUseCase
import com.wemeal.domain.usecase.tag.SearchOrdersUseCase

class MainViewModelFactory(
    private val app: Application,
    private val createPostUseCase: CreatePostUseCase,
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val searchBrandsUseCase: SearchBrandsUseCase,
    private val searchOffersUseCase: SearchOffersUseCase,
    private val searchMealsUseCase: SearchMealsUseCase,
    private val searchOrdersUseCase: SearchOrdersUseCase,
    private val getBrandGalleryUseCase: GetBrandGalleryUseCase,
    private val getFeedUseCase: GetFeedUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val unLikePostUseCase: UnLikePostUseCase,
    private val reportPostUseCase: ReportPostUseCase,
    private val followPostUseCase: FollowPostUseCase,
    private val unFollowPostUseCase: UnFollowPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val getPostUseCase: GetPostUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                app = app,
                createPostUseCase = createPostUseCase,
                uploadImagesUseCase = uploadImagesUseCase,
                searchUsersUseCase = searchUsersUseCase,
                searchBrandsUseCase = searchBrandsUseCase,
                searchOffersUseCase = searchOffersUseCase,
                searchMealsUseCase = searchMealsUseCase,
                searchOrdersUseCase = searchOrdersUseCase,
                getBrandGalleryUseCase = getBrandGalleryUseCase,
                getFeedUseCase = getFeedUseCase,
                likePostUseCase = likePostUseCase,
                unLikePostUseCase = unLikePostUseCase,
                reportPostUseCase = reportPostUseCase,
                followPostUseCase = followPostUseCase,
                unFollowPostUseCase = unFollowPostUseCase,
                deletePostUseCase = deletePostUseCase,
                getPostUseCase = getPostUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown view model")
    }
}