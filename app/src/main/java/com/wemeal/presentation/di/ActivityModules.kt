package com.wemeal.presentation.di

import android.app.Application
import android.content.Context
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.people.v1.PeopleService
import com.google.api.services.people.v1.PeopleServiceScopes
import com.google.api.services.people.v1.model.ListConnectionsResponse
import com.google.firebase.auth.FirebaseAuth
import com.wemeal.BuildConfig
import com.wemeal.data.api.ApiServices
import com.wemeal.data.api.ImageApiService
import com.wemeal.data.repository.auth.*
import com.wemeal.data.repository.datasource.auth.LoginFaceBookDataSource
import com.wemeal.data.repository.datasource.auth.LoginGoogleDataSource
import com.wemeal.data.repository.datasource.feed.*
import com.wemeal.data.repository.datasource.onboarding.PatchUserDataSource
import com.wemeal.data.repository.datasource.gallery.GetBrandGalleryDataSource
import com.wemeal.data.repository.datasource.onboarding.*
import com.wemeal.data.repository.datasource.tag.SearchBrandsDataSource
import com.wemeal.data.repository.datasource.tag.SearchMealsDataSource
import com.wemeal.data.repository.datasource.tag.SearchOffersDataSource
import com.wemeal.data.repository.datasource.tag.SearchOrdersDataSource
import com.wemeal.data.repository.datasourceImpl.auth.LoginFaceBookDataSourceImpl
import com.wemeal.data.repository.datasourceImpl.auth.LoginGoogleDataSourceImpl
import com.wemeal.data.repository.datasourceImpl.feed.*
import com.wemeal.data.repository.datasourceImpl.onboarding.PatchUserDataSourceImpl
import com.wemeal.data.repository.datasourceImpl.gallery.GetBrandGalleryDataSourceImpl
import com.wemeal.data.repository.datasourceImpl.onboarding.*
import com.wemeal.data.repository.datasourceImpl.tag.SearchBrandsDataSourceImpl
import com.wemeal.data.repository.datasourceImpl.tag.SearchMealsDataSourceImpl
import com.wemeal.data.repository.datasourceImpl.tag.SearchOffesDataSourceImpl
import com.wemeal.data.repository.datasourceImpl.tag.SearchOrdersDataSourceImpl
import com.wemeal.data.repository.feed.*
import com.wemeal.data.repository.gallery.GetBrandGalleryRepositoryImpl
import com.wemeal.data.repository.onboarding.*
import com.wemeal.data.repository.tag.SearchBrandsRepositoryImpl
import com.wemeal.data.repository.tag.SearchMealsRepositoryImpl
import com.wemeal.data.repository.tag.SearchOffersRepositoryImpl
import com.wemeal.data.repository.tag.SearchOrdersRepositoryImpl
import com.wemeal.domain.repository.auth.*
import com.wemeal.domain.repository.feed.*
import com.wemeal.domain.repository.gallery.GetBrandGalleryRepository
import com.wemeal.domain.repository.onboarding.*
import com.wemeal.domain.repository.tag.SearchBrandsRepository
import com.wemeal.domain.repository.tag.SearchMealsRepository
import com.wemeal.domain.repository.tag.SearchOffersRepository
import com.wemeal.domain.repository.tag.SearchOrdersRepository
import com.wemeal.domain.usecase.auth.*
import com.wemeal.domain.usecase.feed.*
import com.wemeal.domain.usecase.gallery.GetBrandGalleryUseCase
import com.wemeal.domain.usecase.onboarding.*
import com.wemeal.domain.usecase.tag.SearchBrandsUseCase
import com.wemeal.domain.usecase.tag.SearchMealsUseCase
import com.wemeal.domain.usecase.tag.SearchOffersUseCase
import com.wemeal.domain.usecase.tag.SearchOrdersUseCase
import com.wemeal.presentation.intro.viewModel.AuthViewModelFactory
import com.wemeal.presentation.main.viewmodel.MainViewModelFactory
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModelFactory
import com.wemeal.presentation.util.GooglePlugin
import com.wemeal.presentation.util.googleContactsRequiredFields
import com.wemeal.presentation.util.googlePath
import com.wemeal.presentation.util.interfaces.ProfileFetchingListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class ActivityModules {

//    @Provides
//    fun providesContext(app: Application): Context {
//        return app.applicationContext
//    }
////
//    @Provides
//    fun providesFirebaseAuth(): FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }

    @Provides
    fun providesLoginManager(): LoginManager {
        return LoginManager.getInstance()
    }

    @Provides
    fun providesCallBackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }

    @Provides
    fun providesGoogleCredential(
        httpTransport: HttpTransport,
        jacksonFactory: JacksonFactory
    ): GoogleCredential {
        return GoogleCredential.Builder()
            .setClientSecrets(
                BuildConfig.GOOGLE_WEB_CLIENT_API,
                BuildConfig.GOOGLE_WEB_SECRET,
            )
            .setTransport(httpTransport)
            .setJsonFactory(jacksonFactory)
            .build()
    }

    @Provides
    fun providesGoogleSignClient(context: Context, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    fun provideGooglePlugin(
        googleCredential: GoogleCredential,
        googleTokenRepository: GoogleTokenRepository,
        peopleResponseRepository: PeopleResponseRepository
    ): ProfileFetchingListener {
        return GooglePlugin.getInstance(
            googleCredential = googleCredential,
            googleTokenRepository = googleTokenRepository,
            peopleResponseRepository = peopleResponseRepository
        )
    }

    @Provides
    fun providesPeopleResponse(peopleService: PeopleService): ListConnectionsResponse {
        return peopleService.people().connections()
            .list(googlePath)
            .setRequestMaskIncludeField(googleContactsRequiredFields)
            .execute()
    }

    @Provides
    fun provideGoogleSignIn(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_API)
            .requestScopes(
                Scope(PeopleServiceScopes.CONTACTS_READONLY),
                Scope(PeopleServiceScopes.USER_EMAILS_READ),
                Scope(PeopleServiceScopes.USERINFO_EMAIL),
                Scope(PeopleServiceScopes.USER_PHONENUMBERS_READ)
            )
            .requestServerAuthCode(BuildConfig.GOOGLE_WEB_CLIENT_API)
            .build()
    }

    //REOPOSITORY
    @Provides
    fun providesGoogleTokenRepository(
        httpTransport: HttpTransport,
        jacksonFactory: JacksonFactory
    ): GoogleTokenRepository {
        return GoogleTokenRepositoryImpl(httpTransport, jacksonFactory)
    }

    @Provides
    fun providesPeopleServiceRepository(
        httpTransport: HttpTransport,
        jacksonFactory: JacksonFactory
    ): PeopleServiceRepository {
        return PeopleServiceRepositoryImpl(httpTransport, jacksonFactory)
    }

    @Provides
    fun providesLoginFacebookRepository(loginFaceBookDataSource: LoginFaceBookDataSource): LoginFacebookRepository {
        return LoginFacebookRepositoryImpl(loginFaceBookDataSource)
    }

    @Provides
    fun providesLoginGoogleRepository(loginGoogleDataSource: LoginGoogleDataSource): LoginGoogleRepository {
        return LoginGoogleRepositoryImpl(loginGoogleDataSource)
    }

    @Provides
    fun providesGetNearestAreasRepository(getNearestAreasDataSource: GetNearestAreasDataSource): GetNearestAreasRepository {
        return GetNearestAreasRepositoryImpl(getNearestAreasDataSource)
    }

    @Provides
    fun providesGetCountriesRepository(getCountriesDataSource: GetCountriesDataSource): GetCountriesRepository {
        return GetCountriesRepositoryImpl(getCountriesDataSource)
    }

    @Provides
    fun providesGetCitiesRepository(getCitiesDataSource: GetCitiesDataSource): GetCitiesRepository {
        return GetCitiesRepositoryImpl(getCitiesDataSource)
    }

    @Provides
    fun providesGetAreasRepository(getAreasDataSource: GetAreasDataSource): GetAreasRepository {
        return GetAreasRepositoryImpl(getAreasDataSource)
    }

    @Provides
    fun providesGetSubAreasRepository(getSubAreasDataSource: GetSubAreasDataSource): GetSubAreasRepository {
        return GetSubAreasRepositoryImpl(getSubAreasDataSource)
    }

    @Provides
    fun providesGetSuggestedFoodiesRepository(getSuggestedFoodiesDataSource: GetSuggestedFoodiesDataSource): GetSuggestedFoodiesRepository {
        return GetSuggestedFoodiesRepositoryImpl(getSuggestedFoodiesDataSource)
    }

    @Provides
    fun providesConfirmAreaRepository(confirmAreaDataSource: ConfirmAreaDataSource): ConfirmAreaRepository {
        return ConfirmAreaRepositoryImpl(confirmAreaDataSource)
    }

    @Provides
    fun providesFollowFoodieRepository(followFoodieDataSource: FollowFoodieDataSource): FollowFoodieRepository {
        return FollowFoodieRepositoryImpl(followFoodieDataSource)
    }

    @Provides
    fun providesUnFollowFoodieRepository(unFollowFoodieDataSource: UnFollowFoodieDataSource): UnFollowFoodieRepository {
        return UnFollowFoodieRepositoryImpl(unFollowFoodieDataSource)
    }

    @Provides
    fun providesGetSuggestedBrandsRepository(getSuggestedBrandsDataSource: GetSuggestedBrandsDataSource): GetSuggestedBrandsRepository {
        return GetSuggestedBrandsRepositoryImpl(getSuggestedBrandsDataSource)
    }

    @Provides
    fun providesFollowBrandRepository(followBrandDataSource: FollowBrandDataSource): FollowBrandRepository {
        return FollowBrandRepositoryImpl(followBrandDataSource)
    }

    @Provides
    fun providesUnFollowBrandRepository(unFollowBrandDataSource: UnFollowBrandDataSource): UnFollowBrandRepository {
        return UnFollowBrandRepositoryImpl(unFollowBrandDataSource)
    }

    @Provides
    fun providesCreatePostRepository(createPostDataSource: CreatePostDataSource): CreatePostRepository {
        return CreatePostRepositoryImpl(createPostDataSource)
    }

    @Provides
    fun providesUploadImagesRepository(uploadImagesDataSource: UploadImagesDataSource): UploadImagesRepository {
        return UploadImagesRepositoryImpl(uploadImagesDataSource)
    }

    @Provides
    fun providesSearchUsersRepository(searchUsersDataSource: SearchUsersDataSource): SearchUsersRepository {
        return SearchUsersRepositoryImpl(searchUsersDataSource)
    }

    @Provides
    fun providesSearchBrandsRepository(searchBrandsDataSource: SearchBrandsDataSource): SearchBrandsRepository {
        return SearchBrandsRepositoryImpl(searchBrandsDataSource)
    }

    @Provides
    fun providesSearchOffersRepository(searchOffersDataSource: SearchOffersDataSource): SearchOffersRepository {
        return SearchOffersRepositoryImpl(searchOffersDataSource)
    }

    @Provides
    fun providesSearchMealsRepository(searchMealsDataSource: SearchMealsDataSource): SearchMealsRepository {
        return SearchMealsRepositoryImpl(searchMealsDataSource)
    }

    @Provides
    fun providesSearchOrdersRepository(searchOrdersDataSource: SearchOrdersDataSource): SearchOrdersRepository {
        return SearchOrdersRepositoryImpl(searchOrdersDataSource)
    }

    @Provides
    fun providesGetBrandGalleryRepository(getBrandGalleryDataSource: GetBrandGalleryDataSource): GetBrandGalleryRepository {
        return GetBrandGalleryRepositoryImpl(getBrandGalleryDataSource)
    }

    @Provides
    fun providesGetFeedRepository(getFeedDataSource: GetFeedDataSource): GetFeedRepository {
        return GetFeedRepositoryImpl(getFeedDataSource)
    }

    @Provides
    fun providesLikePostRepository(likePostDataSource: LikePostDataSource): LikePostRepository {
        return LikePostRepositoryImpl(likePostDataSource)
    }

    @Provides
    fun providesUnLikePostRepository(unLikePostDataSource: UnLikePostDataSource): UnLikePostRepository {
        return UnLikePostRepositoryImpl(unLikePostDataSource)
    }

    @Provides
    fun providesFollowPostRepository(followPostDataSource: FollowPostDataSource): FollowPostRepository {
        return FollowPostRepositoryImpl(followPostDataSource)
    }

    @Provides
    fun providesUnFollowPostRepository(unFollowPostDataSource: UnFollowPostDataSource): UnFollowPostRepository {
        return UnFollowPostRepositoryImpl(unFollowPostDataSource)
    }

    @Provides
    fun providesReportPostRepository(reportPostDataSource: ReportPostDataSource): ReportPostRepository {
        return ReportPostRepositoryImpl(reportPostDataSource)
    }

    @Provides
    fun providesDeletePostRepository(deletePostDataSource: DeletePostDataSource): DeletePostRepository {
        return DeletePostRepositoryImpl(deletePostDataSource)
    }

    @Provides
    fun providesGetPostRepository(getPostDataSource: GetPostDataSource): GetPostRepository {
        return GetPostRepositoryImpl(getPostDataSource)
    }

    @Provides
    fun providesPatchUserRepository(patchUserDataSource: PatchUserDataSource): PatchUserRepository {
        return PatchUserRepositoryImpl(patchUserDataSource)
    }

    @Provides
    fun providesLoginRepository(
        @ActivityContext context: Context,
        loginManager: LoginManager,
        callbackManager: CallbackManager,
        firebaseAuth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient,
        profileFetchingListener: ProfileFetchingListener
    ): LoginRepository {
        return LoginRepositoryImpl.getInstance(
            context = context,
            loginManager = loginManager,
            callbackManager = callbackManager,
            firebaseAuth = firebaseAuth,
            googleSignInClient = googleSignInClient,
            profileFetchingListener = profileFetchingListener
        )
    }

    @Provides
    fun providesPeopleResponseRepository(
        peopleServiceRepository: PeopleServiceRepository
    ): PeopleResponseRepository {
        return PeopleResponseRepositoryImpl(peopleServiceRepository = peopleServiceRepository)
    }

    //DATA-SOURCES
    @Provides
    fun providesLoginFaceBookDataSource(apiServices: ApiServices): LoginFaceBookDataSource {
        return LoginFaceBookDataSourceImpl(apiServices)
    }

    @Provides
    fun providesLoginGoogleDataSource(apiServices: ApiServices): LoginGoogleDataSource {
        return LoginGoogleDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetNearestAreasDataSource(apiServices: ApiServices): GetNearestAreasDataSource {
        return GetNearestAreasDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetCountriesDataSource(apiServices: ApiServices): GetCountriesDataSource {
        return GetCountriesDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetCitiesDataSource(apiServices: ApiServices): GetCitiesDataSource {
        return GetCitiesDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetAreasDataSource(apiServices: ApiServices): GetAreasDataSource {
        return GetAreasDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetSubAreasDataSource(apiServices: ApiServices): GetSubAreasDataSource {
        return GetSubAreasDataSourceImpl(apiServices)
    }

    @Provides
    fun providesConfirmAreaDataSource(apiServices: ApiServices): ConfirmAreaDataSource {
        return ConfirmAreaDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetSuggestedFoodiesDataSource(apiServices: ApiServices): GetSuggestedFoodiesDataSource {
        return GetSuggestedFoodiesDataSourceImpl(apiServices)
    }

    @Provides
    fun providesFollowFoodieDataSource(apiServices: ApiServices): FollowFoodieDataSource {
        return FollowFoodieDataSourceImpl(apiServices)
    }

    @Provides
    fun providesUnFollowFoodieDataSource(apiServices: ApiServices): UnFollowFoodieDataSource {
        return UnFollowFoodieDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetSuggestedBrandsDataSource(apiServices: ApiServices): GetSuggestedBrandsDataSource {
        return GetSuggestedBrandsDataSourceImpl(apiServices)
    }

    @Provides
    fun providesFollowBrandDataSource(apiServices: ApiServices): FollowBrandDataSource {
        return FollowBrandDataSourceImpl(apiServices)
    }

    @Provides
    fun providesUnFollowBrandDataSource(apiServices: ApiServices): UnFollowBrandDataSource {
        return UnFollowBrandDataSourceImpl(apiServices)
    }

    @Provides
    fun providesCreatePostDataSource(apiServices: ApiServices): CreatePostDataSource {
        return CreatePostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesUploadImagesDataSource(imagesApiServices: ImageApiService): UploadImagesDataSource {
        return UploadImagesDataSourceImpl(imagesApiServices)
    }

    @Provides
    fun providesSearchUsersDataSource(apiServices: ApiServices): SearchUsersDataSource {
        return SearchUsersDataSourceImpl(apiServices)
    }

    @Provides
    fun providesSearchBrandsDataSource(apiServices: ApiServices): SearchBrandsDataSource {
        return SearchBrandsDataSourceImpl(apiServices)
    }

    @Provides
    fun providesSearchOffersDataSource(apiServices: ApiServices): SearchOffersDataSource {
        return SearchOffesDataSourceImpl(apiServices)
    }

    @Provides
    fun providesSearchMealsDataSource(apiServices: ApiServices): SearchMealsDataSource {
        return SearchMealsDataSourceImpl(apiServices)
    }

    @Provides
    fun providesSearchOrdersDataSource(apiServices: ApiServices): SearchOrdersDataSource {
        return SearchOrdersDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetBrandGalleryDataSource(apiServices: ApiServices): GetBrandGalleryDataSource {
        return GetBrandGalleryDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetFeedDataSource(apiServices: ApiServices): GetFeedDataSource {
        return GetFeedDataSourceImpl(apiServices)
    }

    @Provides
    fun providesLikePostDataSource(apiServices: ApiServices): LikePostDataSource {
        return LikePostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesUnLikePostDataSource(apiServices: ApiServices): UnLikePostDataSource {
        return UnLikePostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesFollowPostDataSource(apiServices: ApiServices): FollowPostDataSource {
        return FollowPostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesUnFollowPostDataSource(apiServices: ApiServices): UnFollowPostDataSource {
        return UnFollowPostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesReportPostDataSource(apiServices: ApiServices): ReportPostDataSource{
        return ReportPostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesDeletePostDataSource(apiServices: ApiServices): DeletePostDataSource{
        return DeletePostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesGetPostDataSource(apiServices: ApiServices): GetPostDataSource{
        return GetPostDataSourceImpl(apiServices)
    }

    @Provides
    fun providesPatchUserDataSource(apiServices: ApiServices): PatchUserDataSource {
        return PatchUserDataSourceImpl(apiServices)
    }

    //USE_CASES
    @Provides
    fun providesLoginGoogleUseCase(loginRepository: LoginRepository): LoginGoogleUseCase {
        return LoginGoogleUseCase(loginRepository)
    }

    @Provides
    fun providesLoginFacebookUseCase(loginRepository: LoginRepository): LoginFacebookUseCase {
        return LoginFacebookUseCase(loginRepository)
    }

    @Provides
    fun providesHandleActivityResultUseCase(loginRepository: LoginRepository): HandleActivityResultUseCase {
        return HandleActivityResultUseCase(loginRepository)
    }

    @Provides
    fun providesLoginGetResultUseCase(loginRepository: LoginRepository): GetResultUseCase {
        return GetResultUseCase(loginRepository)
    }

    @Provides
    fun providesLoginFacebookApiUserCase(loginFacebookRepository: LoginFacebookRepository): LoginFacebookApiUserCase {
        return LoginFacebookApiUserCase(loginFacebookRepository)
    }

    @Provides
    fun providesLoginGoogleApiUserCase(loginGoogleRepository: LoginGoogleRepository): LoginGoogleApiUserCase {
        return LoginGoogleApiUserCase(loginGoogleRepository)
    }

    @Provides
    fun providesPeopleServiceUseCase(peopleServiceRepository: PeopleServiceRepository): PeopleServiceUseCase {
        return PeopleServiceUseCase(peopleServiceRepository)
    }

    @Provides
    fun providesPeopleResponseUseCase(peopleResponseRepository: PeopleResponseRepository): PeopleResponseUseCase {
        return PeopleResponseUseCase(peopleResponseRepository)
    }

    @Provides
    fun providesGoogleTokenUseCase(googleTokenRepository: GoogleTokenRepository): GoogleTokenUseCase {
        return GoogleTokenUseCase(googleTokenRepository)
    }

    @Provides
    fun providesGetNearestAreasUseCase(getNearestAreasRepository: GetNearestAreasRepository): GetNearestAreasUseCase {
        return GetNearestAreasUseCase(getNearestAreasRepository)
    }

    @Provides
    fun providesGetCountriesUseCase(getCountriesRepository: GetCountriesRepository): GetCountriesUseCase {
        return GetCountriesUseCase(getCountriesRepository)
    }

    @Provides
    fun providesGetCitiesUseCase(getCitiesRepository: GetCitiesRepository): GetCitiesUseCase {
        return GetCitiesUseCase(getCitiesRepository)
    }

    @Provides
    fun providesGetAreasUseCase(getAreasRepository: GetAreasRepository): GetAreasUseCase {
        return GetAreasUseCase(getAreasRepository)
    }

    @Provides
    fun providesGetSubAreasUseCase(getSubAreasRepository: GetSubAreasRepository): GetSubAreasUseCase {
        return GetSubAreasUseCase(getSubAreasRepository)
    }

    @Provides
    fun providesConfirmAreaUseCase(confirmAreaRepository: ConfirmAreaRepository): ConfirmAreaUseCase {
        return ConfirmAreaUseCase(confirmAreaRepository)
    }

    @Provides
    fun providesGetSuggestedFoodiesUseCase(getSuggestedFoodiesRepository: GetSuggestedFoodiesRepository): GetSuggestedFoodiesUseCase {
        return GetSuggestedFoodiesUseCase(getSuggestedFoodiesRepository)
    }

    @Provides
    fun providesFollowFoodieUseCase(followFoodieRepository: FollowFoodieRepository): FollowFoodieUseCase {
        return FollowFoodieUseCase(followFoodieRepository)
    }

    @Provides
    fun providesUnFollowFoodieUseCase(unFollowFoodieRepository: UnFollowFoodieRepository): UnFollowFoodieUseCase {
        return UnFollowFoodieUseCase(unFollowFoodieRepository)
    }

    @Provides
    fun providesFollowBrandUseCase(followBrandRepository: FollowBrandRepository): FollowBrandUseCase {
        return FollowBrandUseCase(followBrandRepository)
    }

    @Provides
    fun providesUnFollowBrandUseCase(unFollowBrandRepository: UnFollowBrandRepository): UnFollowBrandUseCase {
        return UnFollowBrandUseCase(unFollowBrandRepository)
    }

    @Provides
    fun providesGetSuggestedBrandsUseCase(getSuggestedBrandsRepository: GetSuggestedBrandsRepository): GetSuggestedBrandsUseCase {
        return GetSuggestedBrandsUseCase(getSuggestedBrandsRepository)
    }

    @Provides
    fun providesCreatePostUseCase(createPostRepository: CreatePostRepository): CreatePostUseCase {
        return CreatePostUseCase(createPostRepository)
    }

    @Provides
    fun providesUploadImagesUseCase(uploadImagesRepository: UploadImagesRepository): UploadImagesUseCase {
        return UploadImagesUseCase(uploadImagesRepository)
    }

    @Provides
    fun providesSearchUsersUseCase(searchUsersRepository: SearchUsersRepository): SearchUsersUseCase {
        return SearchUsersUseCase(searchUsersRepository)
    }

    @Provides
    fun providesSearchBrandsUseCase(searchBrandsRepository: SearchBrandsRepository): SearchBrandsUseCase {
        return SearchBrandsUseCase(searchBrandsRepository)
    }

    @Provides
    fun providesSearchOffersUseCase(searchOffersRepository: SearchOffersRepository): SearchOffersUseCase {
        return SearchOffersUseCase(searchOffersRepository)
    }

    @Provides
    fun providesSearchMealsUseCase(searchMealsRepository: SearchMealsRepository): SearchMealsUseCase {
        return SearchMealsUseCase(searchMealsRepository)
    }

    @Provides
    fun providesSearchOrdersUseCase(searchOrdersRepository: SearchOrdersRepository): SearchOrdersUseCase {
        return SearchOrdersUseCase(searchOrdersRepository)
    }

    @Provides
    fun providesGetBrandGalleryUseCase(getBrandGalleryRepository: GetBrandGalleryRepository): GetBrandGalleryUseCase {
        return GetBrandGalleryUseCase(getBrandGalleryRepository)
    }

    @Provides
    fun providesGetFeedUseCase(getFeedRepository: GetFeedRepository): GetFeedUseCase {
        return GetFeedUseCase(getFeedRepository)
    }

    @Provides
    fun providesLikePostUseCase(likePostRepository: LikePostRepository): LikePostUseCase {
        return LikePostUseCase(likePostRepository)
    }

    @Provides
    fun providesUnLikePostUseCase(unLikePostRepository: UnLikePostRepository): UnLikePostUseCase {
        return UnLikePostUseCase(unLikePostRepository)
    }

    @Provides
    fun providesFollowPostUseCase(followPostRepository: FollowPostRepository): FollowPostUseCase {
        return FollowPostUseCase(followPostRepository)
    }

    @Provides
    fun providesUnFollowPostUseCase(unFollowPostRepository: UnFollowPostRepository): UnFollowPostUseCase {
        return UnFollowPostUseCase(unFollowPostRepository)
    }

    @Provides
    fun providesReportPostUseCase(reportPostRepository: ReportPostRepository): ReportPostUseCase {
        return ReportPostUseCase(reportPostRepository)
    }

    @Provides
    fun providesDeletePostUseCase(deletePostRepository: DeletePostRepository): DeletePostUseCase {
        return DeletePostUseCase(deletePostRepository)
    }

    @Provides
    fun providesGetPostUseCase(getPostRepository: GetPostRepository): GetPostUseCase {
        return GetPostUseCase(getPostRepository)
    }

    @Provides
    fun providesPatchUserUseCase(patchUserRepository: PatchUserRepository): PatchUserUseCase {
        return PatchUserUseCase(patchUserRepository)
    }

    @Provides
    fun providesJacksonFactory(): JacksonFactory {
        return JacksonFactory()
    }

    //VIEW-MODELS-FACTORIES
    @Provides
    fun providesMainViewModelFactory(
        app: Application,
        createPostUseCase: CreatePostUseCase,
        uploadImagesUseCase: UploadImagesUseCase,
        searchUsersUseCase: SearchUsersUseCase,
        searchBrandsUseCase: SearchBrandsUseCase,
        searchOffersUseCase: SearchOffersUseCase,
        searchMealsUseCase: SearchMealsUseCase,
        searchOrdersUseCase: SearchOrdersUseCase,
        getBrandGalleryUseCase: GetBrandGalleryUseCase,
        getFeedUseCase: GetFeedUseCase,
        likePostUseCase: LikePostUseCase,
        unLikePostUseCase: UnLikePostUseCase,
        reportPostUseCase: ReportPostUseCase,
        followPostUseCase: FollowPostUseCase,
        unFollowPostUseCase: UnFollowPostUseCase,
        deletePostUseCase: DeletePostUseCase,
        getPostUseCase: GetPostUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(
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
        )
    }

    @Provides
    fun providesSocialViewModelFactory(
        app: Application,
        loginGoogleUseCase: LoginGoogleUseCase,
        loginFacebookUseCase: LoginFacebookUseCase,
        handleActivityResultUseCase: HandleActivityResultUseCase,
        getResultUseCase: GetResultUseCase,
        loginFacebookApiUserCase: LoginFacebookApiUserCase,
        loginGoogleApiUserCase: LoginGoogleApiUserCase
    ): AuthViewModelFactory {
        return AuthViewModelFactory(
            application = app,
            loginGoogleUseCase = loginGoogleUseCase,
            loginFacebookUseCase = loginFacebookUseCase,
            handleActivityResultUseCase = handleActivityResultUseCase,
            getResultUseCase = getResultUseCase,
            loginFacebookApiUserCase = loginFacebookApiUserCase,
            loginGoogleApiUserCase = loginGoogleApiUserCase
        )
    }

    @Provides
    fun providesOnBoardingViewModelFactory(
        app: Application,
        getNearestAreasUseCase: GetNearestAreasUseCase,
        getCountriesUseCase: GetCountriesUseCase,
        getCitiesUseCase: GetCitiesUseCase,
        getAreasUseCase: GetAreasUseCase,
        getSubAreasUseCase: GetSubAreasUseCase,
        getSuggestedFoodiesUseCase: GetSuggestedFoodiesUseCase,
        getSuggestedBrandsUseCase: GetSuggestedBrandsUseCase,
        confirmAreaUseCase: ConfirmAreaUseCase,
        followFoodieUseCase: FollowFoodieUseCase,
        unFollowFoodieUseCase: UnFollowFoodieUseCase,
        followBrandUseCase: FollowBrandUseCase,
        unfollowBrandUseCase: UnFollowBrandUseCase,
        patchUserUseCase: PatchUserUseCase,
    ): OnBoardingViewModelFactory {
        return OnBoardingViewModelFactory(
            app = app,
            getNearestAreasUseCase = getNearestAreasUseCase,
            getCountriesUseCase = getCountriesUseCase,
            getCitiesUseCase = getCitiesUseCase,
            getAreasUseCase = getAreasUseCase,
            getSubAreasUseCase = getSubAreasUseCase,
            confirmAreaUseCase = confirmAreaUseCase,
            getSuggestedFoodiesUseCase = getSuggestedFoodiesUseCase,
            getSuggestedBrandsUseCase = getSuggestedBrandsUseCase,
            followFoodieUseCase = followFoodieUseCase,
            unFollowFoodieUseCase = unFollowFoodieUseCase,
            followBrandUseCase = followBrandUseCase,
            unFollowBrandUseCase = unfollowBrandUseCase,
            patchUserUseCase = patchUserUseCase,
        )
    }

    @Provides
    fun providesHttpTransport(): HttpTransport {
        return NetHttpTransport()
    }
}