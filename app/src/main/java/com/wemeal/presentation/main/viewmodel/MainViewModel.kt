package com.wemeal.presentation.main.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.*
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import automatic
import com.wemeal.R
import com.wemeal.data.model.NameModel
import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.main.Result
import com.wemeal.data.model.main.SearchUsersModel
import com.wemeal.data.model.main.create.AwsRequestModel
import com.wemeal.data.model.main.create.AwsResponseModel
import com.wemeal.data.model.main.feed.Activity
import com.wemeal.data.model.main.feed.FeedDetailsModel
import com.wemeal.data.model.main.feed.FeedModel
import com.wemeal.data.model.main.feed.actions.delete.DeleteResponseModel
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import com.wemeal.data.model.main.feed.actions.report.ReportResponseModel
import com.wemeal.data.model.main.feed.actions.unlike.UnLikeResponseModel
import com.wemeal.data.model.main.gallery.BrandGalleryModel
import com.wemeal.data.model.main.create.CreatePostResponseModel
import com.wemeal.data.model.main.tagged.brands.SearchedBrands
import com.wemeal.data.model.main.tagged.offers.SearchedOffers
import com.wemeal.data.model.main.tagged.orders.SearchedOrders
import com.wemeal.data.model.main.tagged.products.SearchedProducts
import com.wemeal.domain.usecase.feed.*
import com.wemeal.domain.usecase.gallery.GetBrandGalleryUseCase
import com.wemeal.domain.usecase.tag.SearchBrandsUseCase
import com.wemeal.domain.usecase.tag.SearchMealsUseCase
import com.wemeal.domain.usecase.tag.SearchOffersUseCase
import com.wemeal.domain.usecase.tag.SearchOrdersUseCase
import com.wemeal.presentation.BaseViewModel
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.extensions.isOnline
import com.wemeal.presentation.extensions.logEvent
import com.wemeal.presentation.util.SharedPreferencesManager
import com.wemeal.presentation.util.events.*
import kotlinx.coroutines.*
import me.shouheng.compress.Compress
import java.io.File
import java.lang.ref.WeakReference
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

class MainViewModel(
    app: Application,
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
) : BaseViewModel(app) {

    var postDetails: com.wemeal.data.model.main.feed.Result? = null
    var postPressed: com.wemeal.data.model.main.feed.Result? = null
    private val sharedPreferencesManager = SharedPreferencesManager.instance
    private val context: WeakReference<Context> = WeakReference(app.applicationContext)
    private val createPostMutableLiveData = MutableLiveData<Resource<CreatePostResponseModel>>()
    val createPostLiveData = createPostMutableLiveData

    private val postImageMutableLiveData = MutableLiveData<Resource<AwsResponseModel>>()
    val postImageLiveData: LiveData<Resource<AwsResponseModel>> = postImageMutableLiveData

    private val searchUsersMutableLiveData = MutableLiveData<Resource<SearchUsersModel>>()
    val searchUsersLiveData: LiveData<Resource<SearchUsersModel>> = searchUsersMutableLiveData

    private val searchBrandsMutableLiveData = MutableLiveData<Resource<SearchedBrands>>()
    val searchBrandsLiveData: LiveData<Resource<SearchedBrands>> = searchBrandsMutableLiveData

    private val searchOffersMutableLiveData = MutableLiveData<Resource<SearchedOffers>>()
    val searchOffersLiveData: LiveData<Resource<SearchedOffers>> = searchOffersMutableLiveData

    private val searchMealsMutableLiveData = MutableLiveData<Resource<SearchedProducts>>()
    val searchMealsLiveData: LiveData<Resource<SearchedProducts>> = searchMealsMutableLiveData

    private val searchOrdersMutableLiveData = MutableLiveData<Resource<SearchedOrders>>()
    val searchOrdersLiveData: LiveData<Resource<SearchedOrders>> = searchOrdersMutableLiveData

    private val getGalleryMutableLiveData = MutableLiveData<Resource<BrandGalleryModel>>()
    val getGalleryLiveData: LiveData<Resource<BrandGalleryModel>> = getGalleryMutableLiveData

    private val getFeedMutableLiveData = MutableLiveData<Resource<FeedModel>>()
    val getFeedLiveData: LiveData<Resource<FeedModel>> = getFeedMutableLiveData

    private val likePostMutableLiveData = MutableLiveData<Resource<LikeResponseModel>>()
    val likePostLiveData: LiveData<Resource<LikeResponseModel>> = likePostMutableLiveData

    private val unLikePostMutableLiveData = MutableLiveData<Resource<UnLikeResponseModel>>()
    val unLikePostLiveData: LiveData<Resource<UnLikeResponseModel>> = unLikePostMutableLiveData

    private val reportPostMutableLiveData = MutableLiveData<Resource<ReportResponseModel>>()
    val reportPostLiveData: LiveData<Resource<ReportResponseModel>> = reportPostMutableLiveData

    private val followPostMutableLiveData = MutableLiveData<Resource<SuccessModel>>()
    val followPostLiveData: LiveData<Resource<SuccessModel>> = followPostMutableLiveData

    private val unFollowPostMutableLiveData = MutableLiveData<Resource<SuccessModel>>()
    val unFollowPostLiveData: LiveData<Resource<SuccessModel>> = unFollowPostMutableLiveData

    private val deletePostMutableLiveData = MutableLiveData<Resource<DeleteResponseModel>>()
    val deletePostLiveData: LiveData<Resource<DeleteResponseModel>> = deletePostMutableLiveData

    private val getPostMutableLiveData = MutableLiveData<Resource<FeedDetailsModel>>()
    val getPostLiveData: LiveData<Resource<FeedDetailsModel>> = getPostMutableLiveData

    val postText = mutableStateOf("")
    var imagesList = mutableStateListOf<String>()
    val galleryImageList = mutableStateListOf<String>()
    var usersList: MutableList<Result>? = mutableStateListOf()
    var brandGallery: MutableState<BrandGalleryModel?>? = mutableStateOf(null)
    var feedObject: MutableState<FeedModel?>? = mutableStateOf(null)

    val isTagged = mutableStateOf(false)
    val isInetractionDialogShown = mutableStateOf(false)

    val brandsList: MutableState<List<com.wemeal.data.model.main.tagged.brands.Result?>> =
        mutableStateOf(ArrayList())
    var taggedBrand: MutableState<com.wemeal.data.model.main.tagged.brands.Result?> =
        mutableStateOf(null)

    val offersList: MutableState<List<com.wemeal.data.model.main.tagged.offers.Result?>> =
        mutableStateOf(ArrayList())
    var taggedOffer: MutableState<com.wemeal.data.model.main.tagged.offers.Result?> =
        mutableStateOf(null)

    val mealsList: MutableState<List<com.wemeal.data.model.main.tagged.products.Result?>> =
        mutableStateOf(ArrayList())
    var taggedMeal: MutableState<com.wemeal.data.model.main.tagged.products.Result?> =
        mutableStateOf(null)

    val ordersList: MutableState<List<com.wemeal.data.model.main.tagged.orders.Result?>> =
        mutableStateOf(ArrayList())
    var taggedOrder: MutableState<com.wemeal.data.model.main.tagged.orders.Result?> =
        mutableStateOf(null)

    var placeBrand: MutableState<NameModel?> = mutableStateOf(null)

    var noSearchResults = mutableStateOf(false)
    var isBottomSheetExpanded = MutableLiveData(false)
    var isFocused = MutableLiveData(false)
    val feedListSnapShot = mutableStateListOf<com.wemeal.data.model.main.feed.Result>()

    //    var excludesIds: HashSet<String> = HashSet()
    private var searchJob: Job? = null

    val sharedToBrand = mutableStateOf(false)
    var added = mutableStateOf(false)
    var usersMap: MutableMap<Pattern, Result> = mutableStateMapOf()
    val imagesListSize = mutableStateOf(0)
    var afterId = mutableStateOf("")
    var reportText = mutableStateOf("")
    var isPosting = mutableStateOf(false)  // Keyboard.Opened or Keyboard.Closed
    val isDetailsLoading = mutableStateOf(false)
    val postingText = mutableStateOf("")


    fun clearImagesList() {
        imagesList.clear()
        galleryImageList.clear()
    }

    fun clearTaggedList() {
        brandsList.value = mutableListOf()
        mealsList.value = mutableListOf()
        offersList.value = mutableListOf()
        ordersList.value = mutableListOf()
    }

    fun clearTaggedObjects() {
        taggedBrand.value = null
        taggedMeal.value = null
        taggedOffer.value = null
        taggedOffer.value = null
        brandGallery?.value = null
        isTagged.value = false
        removeBrandGalleryImages()
        galleryImageList.clear()
    }

    fun resetCreatePostData() {
        imagesList.clear()
        galleryImageList.clear()
        isTagged.value = false
        taggedBrand.value = null
        taggedMeal.value = null
        taggedOffer.value = null
        taggedOrder.value = null
        added.value = false
        sharedToBrand.value = false
    }

    private fun filterText(): String {
        var newPostText = postText.value
        for (pattern in usersMap.keys) {
            val user = usersMap[pattern]
            val name = "${user?.firstName} ${user?.lastName} "
            val matcher = pattern.matcher(name)
            if (matcher.find()) {
                val start = newPostText.indexOf(name)
                val end = start + name.length
                if (start == -1 || end > newPostText.length) continue
                newPostText =
                    newPostText.replaceRange(IntRange(start, end - 1), "@${user?.username} ")
            }
        }
        return newPostText
    }

    private fun getTaggedObjectId(): Map<String, String?>? {
        val map = HashMap<String, String?>()
        when {
            taggedBrand.value != null -> map["taggedBrand"] = taggedBrand.value?.id
            taggedMeal.value != null -> map["taggedMeal"] = taggedMeal.value?.id
            taggedOffer.value != null -> map["taggedOffer"] = taggedOffer.value?.id
            else -> map["taggedOrder"] = taggedOrder.value?._id
        }
        return map
    }

    fun createPost() {
        //FOR FIRST TIME RULES AGREEMENT
        isBottomSheetExpanded.value = false
        sharedPreferencesManager.isRulesConfirmed = true
        //FOR FIRST TIME RULES AGREEMENT
        if (isOnline(context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                createPostMutableLiveData.postValue(Resource.Loading())
//                val uploadedImageList = mutableStateListOf<String>()
                val awsList = mutableStateListOf<AwsRequestModel>()

                //Uploading image to AWS
                if (imagesList.isNotEmpty()) {
                    for (imagePath in imagesList) {
                        val file = File(imagePath)
                        val index = imagesList.indexOf(imagePath).plus(1)
                        //in case of images urls

                        if (!file.exists()) {
                            val awsResponseModel =
                                AwsRequestModel(url = imagePath, isRestaurantGallery = true)
                            awsList.add(awsResponseModel)
                            continue
                        }

                        postingText.value = "${context.get()?.getString(R.string.posting_image)} ($index ${context.get()?.getString(R.string.of)} ${imagesList.size})"

                        val compressedImageFile =
                            Compress.with(context.get()!!, file)
                                .automatic {
                                    this.quality = 50
                                    this.format =
                                        when {
                                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> Bitmap.CompressFormat.WEBP_LOSSLESS
                                            else -> Bitmap.CompressFormat.WEBP
                                        }
                                }
                                .get()

                        when (val imageResponse =
                            uploadImagesUseCase.execute(compressedImageFile)) {
                            is Resource.Success ->
                                imageResponse.data?.let {
                                    val awsResponseModel = AwsRequestModel(
                                        url = it.imageUrl,
                                        isRestaurantGallery = false,
                                        width = it.width,
                                        height = it.height,
                                        orientation = it.orientation
                                    )
                                    awsList.add(awsResponseModel)
                                }
                            else -> postImageMutableLiveData.postValue(imageResponse)
                        }
                    }
                }

                //UPLOADING IMAGE TO AWS FIRST AND GET THEIR URLS
                withContext(Dispatchers.Default) {
                    postingText.value = context.get()?.getString(R.string.posting) ?: ""

                    val map = getTaggedObjectId()

                    val response =
                        createPostUseCase.execute(
                            text = filterText(),
                            taggedObject = map?.values?.toList()?.getOrNull(0),
                            objectType = map?.keys?.toList()?.getOrNull(0),
                            shareToBrand = sharedToBrand.value,
                            images = awsList
                        )
                    createPostMutableLiveData.postValue(response)
                }

            }
        } else
            createPostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun searchUsers(search: String) {
        if (isOnline(context.get()!!)) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(500)
                var excludesIds = ""
                usersMap.forEach { user ->
                    excludesIds += "${user.value.id.toString()},"
                }

                searchUsersMutableLiveData.postValue(Resource.Loading())
                val response =
                    searchUsersUseCase.execute(
                        search = search,
                        excludesIds = when {
                            excludesIds.isNotEmpty() -> excludesIds.substring(
                                0,
                                excludesIds.length - 1
                            )
                            else -> ""
                        }
                    )
                searchUsersMutableLiveData.postValue(response)
            }
        } else
            searchUsersMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun searchBrands(search: String) {
        searchJob?.cancel()
        noSearchResults.value = false
        if (isOnline(context.get()!!)) {
            searchJob = viewModelScope.launch {
                delay(500)
                searchBrandsMutableLiveData.postValue(Resource.Loading())
                val response =
                    searchBrandsUseCase.execute(search = search)
                searchBrandsMutableLiveData.postValue(response)
            }
        } else
            searchBrandsMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun searchOffers(search: String) {
        searchJob?.cancel()
        if (isOnline(context.get()!!)) {
            noSearchResults.value = false
            searchJob = viewModelScope.launch {
                delay(500)
                searchOffersMutableLiveData.postValue(Resource.Loading())
                val response =
                    searchOffersUseCase.execute(search = search)
                searchOffersMutableLiveData.postValue(response)
            }
        } else
            searchOffersMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun searchMeals(search: String) {
        searchJob?.cancel()
        if (isOnline(context.get()!!)) {
            noSearchResults.value = false
            searchJob = viewModelScope.launch {
                delay(500)
                searchMealsMutableLiveData.postValue(Resource.Loading())
                val response =
                    searchMealsUseCase.execute(search = search)
                searchMealsMutableLiveData.postValue(response)
            }
        } else
            searchMealsMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun searchOrders(search: String) {
        searchJob?.cancel()
        if (isOnline(context.get()!!)) {
            noSearchResults.value = false
            searchJob = viewModelScope.launch {
                delay(500)
                searchOrdersMutableLiveData.postValue(Resource.Loading())
                val response =
                    searchOrdersUseCase.execute(search = search)
                searchOrdersMutableLiveData.postValue(response)
            }
        } else
            searchOrdersMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun getGallery(placeId: String, objectId: String, objectType: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                getGalleryMutableLiveData.postValue(Resource.Loading())
                val response =
                    getBrandGalleryUseCase.execute(
                        placeId = placeId,
                        objectId = objectId,
                        objectType = objectType
                    )
                getGalleryMutableLiveData.postValue(response)
            }
        } else
            getGalleryMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun getBrandGallery(placeId: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                getGalleryMutableLiveData.postValue(Resource.Loading())
                val response =
                    getBrandGalleryUseCase.execute(
                        placeId = placeId,
                        objectId = null,
                        objectType = null
                    )
                getGalleryMutableLiveData.postValue(response)
            }
        } else
            getGalleryMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    var feedJob: Job? = null

    fun getFeed() {
        feedJob?.cancel()
        if (isOnline(context.get()!!)) {
            feedJob = viewModelScope.launch {
                if (afterId.value.isEmpty())
                    getFeedMutableLiveData.postValue(Resource.Loading())

                val response = getFeedUseCase.execute(afterId = afterId.value)
                getFeedMutableLiveData.postValue(response)
            }
        } else
            getGalleryMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    private fun setSelectedPost(_id: String) {
        postPressed = feedListSnapShot?.single { s -> s.activity._id == _id }
    }

    fun likePost(_id: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                setSelectedPost(_id)
                likePostMutableLiveData.postValue(Resource.Loading())
                val response = likePostUseCase.execute(id = _id)
                likePostMutableLiveData.postValue(response)
            }
        } else
            likePostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun unLikePost(_id: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                setSelectedPost(_id)
                unLikePostMutableLiveData.postValue(Resource.Loading())
                val response = unLikePostUseCase.execute(id = _id)
                unLikePostMutableLiveData.postValue(response)
            }
        } else
            unLikePostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun reportPost(_id: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                setSelectedPost(_id = _id)
                reportPostMutableLiveData.postValue(Resource.Loading())
                val response = reportPostUseCase.execute(id = _id, reason = reportText.value)
                reportPostMutableLiveData.postValue(response)
            }
        } else
            reportPostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun getPost(_id: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                setSelectedPost(_id = _id)
                getPostMutableLiveData.postValue(Resource.Loading())
                val response = getPostUseCase.execute(id = _id)
                getPostMutableLiveData.postValue(response)
            }
        } else
            getPostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun followPost(_id: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                setSelectedPost(_id = _id)
                followPostMutableLiveData.postValue(Resource.Loading())
                val response = followPostUseCase.execute(id = _id)
                followPostMutableLiveData.postValue(response)
            }
        } else
            followPostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun unFollowPost(_id: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                setSelectedPost(_id = _id)
                unFollowPostMutableLiveData.postValue(Resource.Loading())
                val response = unFollowPostUseCase.execute(id = _id)
                unFollowPostMutableLiveData.postValue(response)
            }
        } else
            unFollowPostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun deletePost(_id: String) {
        if (isOnline(context.get()!!)) {
            viewModelScope.launch {
                setSelectedPost(_id = _id)
                deletePostMutableLiveData.postValue(Resource.Loading())
                val response = deletePostUseCase.execute(id = _id)
                deletePostMutableLiveData.postValue(response)
            }
        } else
            unFollowPostMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun removeBrandGalleryImages() {
        galleryImageList.forEach {
            when {
                imagesList.contains(it) -> imagesList.remove(it)
            }
        }
    }

    fun getObjectType(): String {
        return when {
            taggedBrand.value != null -> return ObjectType.RESTAURANT.name
            taggedMeal.value != null -> ObjectType.MEAL.name
            taggedOffer.value != null -> ObjectType.OFFER.name
            else -> ObjectType.NA.name
        }
    }

    fun sendCreatePostLog(eventName: CustomEvent) {
        context.get().activity()
            ?.logEvent(
                event = eventName,
                eventCase = EventCase.SUCCESS,
                bundle = bundleOf(
                    CustomEventParams.OBJECT_TYPE.name to getObjectType(),
                    CustomEventParams.POST_WITH_TAGGED_OBJECT.name to (if (isTagged.value) 1 else 0),
                    CustomEventParams.POST_WITH_TEXT.name to (if (postText.value.isNotEmpty()) 1 else 0),
                    CustomEventParams.POST_WITH_IMAGE.name to (if (imagesList.isNotEmpty()) 1 else 0)
                )
            )
    }

    fun updateFeedList(errorMessage: String?): Activity {
        return feedListSnapShot.single { s -> s.activity._id == postPressed?.activity?._id }.activity.apply {
            if (errorMessage?.contains("removed", false) == true)
                isPostDeleted?.value = true
        }
    }

    //this function for mutating social actions and counts offline
    fun setItemMutableValues(item: com.wemeal.data.model.main.feed.Result?) {
        item?.activity?.isLikedMutable = mutableStateOf(item?.activity?.isLiked)
        item?.activity?.likeCountMutable = mutableStateOf(item?.activity?.likesCount)

        if (item?.activity?.isConfirmShowing == null)
            item?.activity?.isConfirmShowing = mutableStateOf(false)
        if (item?.activity?.isUnFollowed == null)
            item?.activity?.isUnFollowed = mutableStateOf(false)
        if (item?.activity?.isUserDeleting == null)
            item?.activity?.isUserDeleting = mutableStateOf(false)
        if (item?.activity?.isPostDeleted == null)
            item?.activity?.isPostDeleted = mutableStateOf(false)
    }

    fun clear() {
        getPostMutableLiveData.value = Resource.Loading() /*or null*/
    }

}
