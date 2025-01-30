package com.wemeal.presentation.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.auth.FirebaseAuth
import com.readystatesoftware.chuck.ChuckInterceptor
import com.wemeal.BuildConfig
import com.wemeal.data.api.ApiServices
import com.wemeal.data.api.ImageApiService
import com.wemeal.data.room.AppDatabase
import com.wemeal.presentation.util.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class Modules {

    //ROOM-DB
//    @Provides
//    fun provideRoomDB(@ApplicationContext context: Context): RoomDatabase {
//        return Room.databaseBuilder(context, AppDatabase::class.java, "social-db").build()
//    }

    //RETROFIT
    @Provides
    fun provideImageApiServices(okHttpClient: OkHttpClient): ImageApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request()
                    val header: MutableMap<String, String> =
                        HashMap()
                    header["Authorization"] =
                        "JWT ${SharedPreferencesManager.instance.user?.accessToken}"
                    header["role"] = "user"
                     val newRequest: Request = request.newBuilder()
                        .headers(header.toHeaders())
                        .build()
                    chain.proceed(newRequest)
                })
            }.build())
            .baseUrl(BuildConfig.AWS_URL).build().create(ImageApiService::class.java)
    }

    @Provides
    fun provideApiServices(okHttpClient: OkHttpClient): ApiServices {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL).build().create(ApiServices::class.java)
    }

    @Provides
    fun providesOkhttpClient(@ApplicationContext context: Context): okhttp3.OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(30, TimeUnit.SECONDS)
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                writeTimeout(100, TimeUnit.SECONDS)
                readTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(ChuckInterceptor(context))
                addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request()
                    val header: MutableMap<String, String> =
                        HashMap()
                    header["language"] = "en"
                    header["platform"] = "android"
                    header["app-version"] = BuildConfig.VERSION_NAME
                    header["Authorization"] =
                        "JWT ${SharedPreferencesManager.instance.user?.accessToken}"
                    val newRequest: Request = request.newBuilder()
                        .headers(header.toHeaders())
                        .build()
                    chain.proceed(newRequest)
                })
            }
            .build()
    }

    @Provides
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun providesContext(app: Application): Context {
        return app.applicationContext
    }
}
