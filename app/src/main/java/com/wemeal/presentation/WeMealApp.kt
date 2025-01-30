package com.wemeal.presentation

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.google.android.libraries.places.api.Places
import com.mazenrashed.logdnaandroidclient.LogDna
import com.wemeal.BuildConfig
import com.wemeal.R
import com.wemeal.presentation.util.SharedPreferencesManager
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class WeMealApp : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPreferencesManager.instance.initialize(this)

        if (!Places.isInitialized()) {
            Places.initialize(
                applicationContext,
                getString(R.string.googgleAPIKey),
                Locale("ar")
            )
        }
        LogDna.init(
            BuildConfig.LogDNA_APIKEY, BuildConfig.LOGDNA_APP_NAME, "WeMeal Android"
        )

        FacebookSdk.fullyInitialize()
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
    }
}