package com.wemeal.presentation.onboarding.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.maps.model.LatLng
import com.wemeal.R
import com.wemeal.data.model.Resource
import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.model.onboarding.countries.Result
import com.wemeal.data.model.onboarding.nearest.NearestModel
import com.wemeal.data.model.onboarding.suggested_brands.BrandsModel
import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel
import com.wemeal.domain.usecase.auth.PatchUserUseCase
import com.wemeal.domain.usecase.onboarding.*
import com.wemeal.presentation.BaseViewModel
import com.wemeal.presentation.extensions.isOnline
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class OnBoardingViewModel(
    app: Application,
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
    private val followBrandsUseCase: FollowBrandUseCase,
    private val unFollowBrandUseCase: UnFollowBrandUseCase,
    private val patchUserUseCase: PatchUserUseCase
) : BaseViewModel(app) {

    private var nearestLocationMutableLiveData = MutableLiveData<Resource<NearestModel>>()
    var nearestLocationLiveData: LiveData<Resource<NearestModel>> =
        nearestLocationMutableLiveData

    private var citiesMutableLiveData = MutableLiveData<Resource<CountriesModel>>()
    var citiesLiveData: LiveData<Resource<CountriesModel>> = citiesMutableLiveData

    private var areasMutableLiveData = MutableLiveData<Resource<CountriesModel>>()
    var areasLiveData: MutableLiveData<Resource<CountriesModel>> = areasMutableLiveData

    private var subAreasMutableLiveData = MutableLiveData<Resource<CountriesModel>>()
    var subAreasLiveData: MutableLiveData<Resource<CountriesModel>> = subAreasMutableLiveData

    private var confirmAreaMutableLiveDate = MutableLiveData<Resource<SuccessModel>>()
    var confirmAreaLiveDate: MutableLiveData<Resource<SuccessModel>> = confirmAreaMutableLiveDate

    private var foodiesMutableLiveData = MutableLiveData<Resource<FoodiesModel>>()
    var foodiesLiveData: LiveData<Resource<FoodiesModel>> = foodiesMutableLiveData

    private var followMutableLiveData = MutableLiveData<Resource<SuccessModel>>()
    var followLiveData: LiveData<Resource<SuccessModel>> = followMutableLiveData

    private var unFollowMutableLiveData = MutableLiveData<Resource<SuccessModel>>()
    var unFollowLiveData: LiveData<Resource<SuccessModel>> = unFollowMutableLiveData

    private var brandsMutableLiveData = MutableLiveData<Resource<BrandsModel>>()
    var brandsLiveData: LiveData<Resource<BrandsModel>> = brandsMutableLiveData

    private var patchUserLiveData = MutableLiveData<Resource<SuccessModel>>()

    val citiesList: MutableState<List<Result>> = mutableStateOf(ArrayList())
    val areasList: MutableState<List<Result>> = mutableStateOf(ArrayList())
    val subAreasList: MutableState<List<Result>> = mutableStateOf(ArrayList())
    var selectedLocation: LatLng? by mutableStateOf(LatLng(31.208870397601984, 29.90701239556074))//

    val foodiesList: MutableState<List<com.wemeal.data.model.onboarding.suggested_foodies.Result>> =
        mutableStateOf(ArrayList())

    val brandsList: MutableState<List<com.wemeal.data.model.onboarding.suggested_brands.Result>> =
        mutableStateOf(ArrayList())

    // Pagination starts at '1' (-1 = exhausted)
    val citiesPage = mutableStateOf(1)
    val areasPage = mutableStateOf(1)
    val subAreasPage = mutableStateOf(1)
    private var citiesRecipeListScrollPosition = 0
    private var areasRecipeListScrollPosition = 0
    private var subsRecipeListScrollPosition = 0

    var foodieId = mutableStateOf("")
    var brandId = mutableStateOf("")

    var locationModel: MutableState<NearestModel?> = mutableStateOf(null)
    val context: WeakReference<Context> = WeakReference(app.applicationContext)

    private var locationJob: Job? = null
    private var patchJob: Job? = null

    private val sharedPreferencesManager = SharedPreferencesManager.instance

    //===================MAP LOCATION PART=====================
    fun getNearestLocation(location: LatLng) {
        locationJob?.cancel()
        if (isOnline(context = context.get()!!)) {
            locationJob = viewModelScope.launch(Dispatchers.IO) {
                nearestLocationMutableLiveData.postValue(Resource.Loading())
                val response =
                    getNearestAreasUseCase.execute(
                        lat = location.latitude,
                        lng = location.longitude
//                        lat = 31.25,
//                        lng = 29.95
                    )
                nearestLocationMutableLiveData.postValue(response)
            }
        } else
            context.get()?.getString(R.string.internet_not_working)?.let { Log.e("ERROR", it) }
    }

    fun getCities(searchText: String) {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                citiesMutableLiveData.postValue(Resource.Loading())
                val response =
                    getCitiesUseCase.execute(page = citiesPage.value, searchText = searchText)
                citiesMutableLiveData.postValue(response)
            }
        } else
            citiesMutableLiveData.postValue(Resource.Error(
                context.get()?.getString(R.string.internet_not_working)))
    }

    fun getAreas(cityID: String, searchText: String) {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                areasMutableLiveData.postValue(Resource.Loading())
                val response =
                    getAreasUseCase.execute(
                        cityId = cityID,
                        searchText = searchText,
                        page = areasPage.value
                    )
                areasMutableLiveData.postValue(response)
            }
        } else
            areasMutableLiveData.postValue(Resource.Error(
                context.get()?.getString(R.string.internet_not_working)))
    }

    fun getSubAreas(areaId: String, searchText: String) {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                subAreasLiveData.postValue(Resource.Loading())
                val response =
                    getSubAreasUseCase.execute(
                        areaId = areaId,
                        searchText = searchText,
                        page = subAreasPage.value
                    )
                subAreasLiveData.postValue(response)
            }
        } else
            subAreasLiveData.postValue(Resource.Error(
                context.get()?.getString(R.string.internet_not_working)))
    }

    fun appendCities(recipes: List<Result>) {
        if (this.citiesList.value.containsAll(recipes)) return
        val current = ArrayList(this.citiesList.value)
        current.addAll(recipes)
        this.citiesList.value = current
    }

    fun appendAreas(recipes: List<Result>) {
        if (this.areasList.value.containsAll(recipes)) return
        val current = ArrayList(this.areasList.value)
        current.addAll(recipes)
        this.areasList.value = current
    }

    fun appendSubAreas(recipes: List<Result>) {
        if (this.subAreasList.value.containsAll(recipes)) return
        val current = ArrayList(this.subAreasList.value)
        current.addAll(recipes)
        this.subAreasList.value = current
    }

    fun nextPage(listType: String, _id: String, searchText: String, page: MutableState<Int>) {
        viewModelScope.launch {
            // prevent duplicate event due to recompose happening to quickly
            val recipeListScrollPosition = when {
                listType.contains(CITIES) -> citiesRecipeListScrollPosition
                listType.contains(SUB_AREAS) -> subsRecipeListScrollPosition
                else -> areasRecipeListScrollPosition
            }

            if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                Log.d("TAG", "nextPage: triggered: ${page.value}")
//                context.get()?.shortToast(page.value.toString())
                when {
                    listType.contains(CITIES) -> incrementPage(citiesPage)
                    listType.contains(SUB_AREAS) -> incrementPage(subAreasPage)
                    else -> incrementPage(areasPage)
                }
                // just to show pagination, api is fast
                delay(500)
                if (page.value > 1) {
                    when {
                        listType.contains(CITIES) -> getCities(searchText)
                        listType.contains(SUB_AREAS) -> getSubAreas(_id, searchText)
                        else -> getAreas(_id, searchText)
                    }
                }
            }
        }
    }

    fun resetData(listType: String) {
        when {
            listType.contains(CITIES) -> resetCitiesSearchState()
            listType.contains(SUB_AREAS) -> resetSubAreasSearchState()
            else -> resetAreasSearchState()
        }
    }

    private fun resetCitiesSearchState() {
        citiesList.value = listOf()
        citiesMutableLiveData.value?.data = null
        citiesPage.value = 1
        onChangeRecipeScrollPosition(CITIES, 0)
    }

    private fun resetAreasSearchState() {
        areasList.value = listOf()
        areasMutableLiveData.value?.data = null
        areasPage.value = 1
        onChangeRecipeScrollPosition(AREAS, 0)
    }

    private fun resetSubAreasSearchState() {
        subAreasList.value = listOf()
        subAreasMutableLiveData.value?.data = null
        subAreasPage.value = 1
        onChangeRecipeScrollPosition(SUB_AREAS, 0)
    }

    fun onChangeRecipeScrollPosition(listType: String, position: Int) {
        when {
            listType.contains(CITIES) -> citiesRecipeListScrollPosition = position
            listType.contains(SUB_AREAS) -> subsRecipeListScrollPosition = position
            else -> areasRecipeListScrollPosition = position
        }
    }

    private fun incrementPage(page: MutableState<Int>) {
        page.value = page.value + 1
    }

    fun confirmArea() {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                confirmAreaMutableLiveDate.postValue(Resource.Loading())
                val response =
                    confirmAreaUseCase.execute(areaModel = locationModel.value?.result?.get(0)!!)
                confirmAreaMutableLiveDate.postValue(response)
            }
        } else
            confirmAreaMutableLiveDate.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    //===================MAP LOCATION PART=====================
    //===================SUGGESTED FOODIES PART=====================
    fun getSuggestedFoodies() {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                foodiesMutableLiveData.postValue(Resource.Loading())
                val response =
                    getSuggestedFoodiesUseCase.execute()
                foodiesMutableLiveData.postValue(response)
            }
        } else
            foodiesMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun followFoodie() {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                followMutableLiveData.postValue(Resource.Loading())
                val response =
                    followFoodieUseCase.execute(foodieId = foodieId.value)
                followMutableLiveData.postValue(response)
            }
        } else
            followMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun unFollowFoodie() {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                unFollowMutableLiveData.postValue(Resource.Loading())
                val response =
                    unFollowFoodieUseCase.execute(foodieId = foodieId.value)
                unFollowMutableLiveData.postValue(response)
            }
        } else
            unFollowMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    //===================SUGGESTED FOODIES PART=====================
    //===================SUGGESTED BRANDS PART=====================
    fun getSuggestedBrands() {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                brandsMutableLiveData.postValue(Resource.Loading())
                val response =
                    getSuggestedBrandsUseCase.execute()
                brandsMutableLiveData.postValue(response)
            }
        } else
            brandsMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun followBrand() {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                followMutableLiveData.postValue(Resource.Loading())
                val response =
                    followBrandsUseCase.execute(brandId = brandId.value)
                followMutableLiveData.postValue(response)
            }
        } else
            followMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }

    fun unFollowBrand() {
        if (isOnline(context = context.get()!!)) {
            viewModelScope.launch(Dispatchers.IO) {
                unFollowMutableLiveData.postValue(Resource.Loading())
                val response =
                    unFollowBrandUseCase.execute(brandId = brandId.value)
                unFollowMutableLiveData.postValue(response)
            }
        } else
            unFollowMutableLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }
    //===================SUGGESTED BRANDS PART=====================
    //===================FINISHING PART=====================
    fun updateUser() {
        sharedPreferencesManager.isDoneOnBoarding = true
        patchJob?.cancel()//for preventing duplicate recall due to recomposing
        if (isOnline(context = context.get()!!)) {
            patchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(1000)//for preventing duplicate recall due to recomposing
                patchUserLiveData.postValue(Resource.Loading())
                val response =
                    patchUserUseCase.execute()
                patchUserLiveData.postValue(response)
            }
        } else
            patchUserLiveData.postValue(
                Resource.Error(
                    context.get()?.getString(R.string.internet_not_working)
                )
            )
    }
    //===================FINISHING PART=====================
}
//var locationModel = MutableLiveData<NearestModel>()


//    val movies: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
//        GetCitiesRepositoryImpl(getCitiesDataSource)
//    }.flow
//        .cachedIn(viewModelScope)
//    fun getNearestLocation(location:LatLng) = liveData {
//        val response = getNearestAreasUseCase.execute(lat = location.latitude, lng = location.longitude)
//        emit(response)
//    }

//    fun getCountries(page: Int) {
//        if (isOnline(context = context.get()!!)) {
//            viewModelScope.launch(Dispatchers.IO) {
//                countriesLiveData.postValue(Resource.Loading())
//                val response =
//                    getCountriesUseCase.execute(page = page)
//                countriesLiveData.postValue(response)
//            }
//        } else
//            context.get()?.notInternetToast()
//    }