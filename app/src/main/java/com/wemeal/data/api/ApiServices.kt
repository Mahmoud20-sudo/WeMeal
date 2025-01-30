package com.wemeal.data.api

import com.wemeal.data.model.SuccessModel
import com.wemeal.data.model.main.SearchUsersModel
import com.wemeal.data.model.main.feed.FeedDetailsModel
import com.wemeal.data.model.main.feed.FeedModel
import com.wemeal.data.model.main.feed.actions.delete.DeleteResponseModel
import com.wemeal.data.model.main.feed.actions.like.LikeResponseModel
import com.wemeal.data.model.main.feed.actions.report.ReportRequestModel
import com.wemeal.data.model.main.feed.actions.report.ReportResponseModel
import com.wemeal.data.model.main.feed.actions.unlike.UnLikeResponseModel
import com.wemeal.data.model.main.gallery.BrandGalleryModel
import com.wemeal.data.model.onboarding.countries.CountriesModel
import com.wemeal.data.model.params.AccessTokenModel
import com.wemeal.data.model.onboarding.suggested_brands.BrandsModel
import com.wemeal.data.model.onboarding.suggested_foodies.FoodiesModel
import com.wemeal.data.model.params.add_area.AddAreaModel
import com.wemeal.data.model.main.create.CreatePostRequestModel
import com.wemeal.data.model.main.create.CreatePostResponseModel
import com.wemeal.data.model.main.tagged.brands.SearchedBrands
import com.wemeal.data.model.main.tagged.offers.SearchedOffers
import com.wemeal.data.model.main.tagged.orders.SearchedOrders
import com.wemeal.data.model.main.tagged.products.SearchedProducts
import com.wemeal.data.model.onboarding.nearest.NearestModel
import com.wemeal.data.model.user.UserModel
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    //==============================AUTH=========================================
    @POST(value = "users/v1/auth/login-facebook")
    suspend fun loginFB(
        @Body accessTokenModel: AccessTokenModel
    ): Response<UserModel>

    @POST(value = "users/v1/auth/login-google")
    suspend fun loginGoogle(
        @Body accessTokenModel: AccessTokenModel
    ): Response<UserModel>

    //==============================ONBOARDING===================================
    @GET(value = "places/v1/areas/nearest")
    suspend fun nearestLocation(
        @Query("lat") lat: Double, @Query("lng") lng: Double
    ): Response<NearestModel>

    @GET(value = "places/v1/countries")
    suspend fun getCountries(
        @Query("page") page: Int,
        @Query("search") search: String = "",
        @Query("limitPerPage") limit: Int = 10
    ): Response<CountriesModel>

    @GET(value = "places/v1/countries/{country_id}/cities")
    suspend fun getCities(
        @Path("country_id") countryId: String = "5bb4e2392e2979000f917b0e",//EG
        @Query("page") page: Int,
        @Query("search") search: String = "",
        @Query("limit") limit: Int = 10
    ): Response<CountriesModel>

    @GET(value = "places/v1/cities/{city_id}/areas")
    suspend fun getAreas(
        @Path("city_id") cityId: String,
        @Query("page") page: Int,
        @Query("search") search: String = "",
        @Query("limit") limit: Int = 10
    ): Response<CountriesModel>

    @GET(value = "places/v1/areas/subareas")
    suspend fun getSubAreas(
        @Query("areaName") areaName: String,
        @Query("page") page: Int,
        @Query("search") search: String = "",
        @Query("limit") limit: Int = 10
    ): Response<CountriesModel>

    @GET(value = "users/v1/users/suggested-foodies")
    suspend fun getSuggestedFoodies(): Response<FoodiesModel>

    @GET(value = "places/v1/places/suggested-brands")
    suspend fun getSuggestedBrands(): Response<BrandsModel>

    @POST(value = "users/v1/users/{foodie_id}/follow")
    suspend fun followFoodie(@Path("foodie_id") foodieId: String): Response<SuccessModel>

    @POST(value = "users/v1/users/{foodie_id}/unfollow")
    suspend fun unFollowFoodie(@Path("foodie_id") foodieId: String): Response<SuccessModel>

    @POST(value = "places/v1/places/{brand_id}/follow")
    suspend fun followBrand(@Path("brand_id") brandId: String): Response<SuccessModel>

    @POST(value = "places/v1/places/{brand_id}/unfollow")
    suspend fun unFollowBrand(@Path("brand_id") brandId: String): Response<SuccessModel>

    @POST(value = "users/v1/users/me/recent-areas")
    suspend fun confirmArea(@Body addAreaModel: AddAreaModel): Response<SuccessModel>

    @PATCH(value = "users/v1/users/me/finalize-onboarding")//users/v1/users/me
    suspend fun patchUser(): Response<SuccessModel>

    //==============================CREATE-POST===================================
    @POST(value = "posts/v1/posts")
    suspend fun createPost(@Body createPostRequestModel: CreatePostRequestModel): Response<CreatePostResponseModel>

    @GET(value = "users/v1/users/search")
    suspend fun searchUsers(
        @Query("excludeIds") excludeIds: String?,
        @Query("search") search: String
    ): Response<SearchUsersModel>

    //==============================TAGGED-OBJECT===================================
    @GET(value = "places/v1/places/search")
    suspend fun searchBrands(
        @Query("search") search: String
    ): Response<SearchedBrands>

    @GET(value = "offers/v1/offers/search")
    suspend fun searchOffers(
        @Query("search") search: String
    ): Response<SearchedOffers>

    @GET(value = "products/v1/products/search")
    suspend fun searchMeals(
        @Query("search") search: String
    ): Response<SearchedProducts>

    @GET(value = "orders/v1/orders/search")
    suspend fun searchOrders(
        @Query("search") search: String
    ): Response<SearchedOrders>

    @GET(value = "places/v1/places/{place_id}/gallery")//offer/meal/order only
    suspend fun getGallery(
        @Path("place_id") placeId: String,
        @Query("taggedObject") objectId: String?,
        @Query("objectType") objectType: String?
    ): Response<BrandGalleryModel>

    @GET(value = "places/v1/places/{place_id}/gallery")//Brand only
    suspend fun getBrandGallery(
        @Path("place_id") placeId: String
    ): Response<BrandGalleryModel>

    @GET(value = "feed/v1/feed")
    suspend fun getFeed(
        @Query("after_id") afterId: String?,
        @Query("limit") limit: Int = 20,
        @Query("content type 1") type: String = "posts",
    ): Response<FeedModel>

    @POST(value = "posts/v1/posts/{post_id}/like")
    suspend fun likePost(@Path("post_id") id: String): Response<LikeResponseModel>

    @POST(value = "posts/v1/posts/{post_id}/unlike")
    suspend fun unLikePost(@Path("post_id") id: String): Response<UnLikeResponseModel>

    @POST(value = "posts/v1/posts/{post_id}/report")
    suspend fun reportPost(@Path("post_id") id: String, @Body reportRequestModel: ReportRequestModel): Response<ReportResponseModel>

    @POST(value = "/users/v1/users/{user_id}/follow")
    suspend fun followPost(@Path("user_id") id: String): Response<SuccessModel>

    @POST(value = "users/v1/users/{user_id}/unfollow")
    suspend fun unfollowPost(@Path("user_id") id: String): Response<SuccessModel>

    @DELETE(value = "posts/v1/posts/{post_id}")
    suspend fun deletePost(@Path("post_id") id: String): Response<DeleteResponseModel>

    @GET(value = "posts/v1/posts/{post_id}")
    suspend fun getPostById(@Path("post_id") id: String): Response<FeedDetailsModel>
}