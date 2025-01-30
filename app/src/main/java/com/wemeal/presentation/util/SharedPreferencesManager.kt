package com.wemeal.presentation.util

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.wemeal.data.model.user.UserModel

class SharedPreferencesManager private constructor() {
    fun initialize(context: Context?): SharedPreferencesManager {
        Hawk.init(context).build()
        return instance
    }

    var user : UserModel?
        get() = Hawk.get(USER)
        set(transaction) {
            Hawk.put(USER, transaction)
        }

    var isDoneOnBoarding : Boolean?
        get() = user?.isDoneOnboarding
        set(value) {
            val mUser = user
            mUser?.isDoneOnboarding = value
            user = mUser
        }

    var isFirstTime : Boolean
        get() = Hawk.get(FIRST_TIME) ?: true
        set(value) {
            Hawk.put(FIRST_TIME, value)
        }

    var isRulesConfirmed : Boolean
        get() = Hawk.get(RULES_AGREED) ?: false
        set(value) {
            Hawk.put(RULES_AGREED, value)
        }

    var postsCount : Int?
        get() = user?.numPosts
        set(value) {
            val mUser = user
            mUser?.numPosts = (mUser?.numPosts ?: 0).plus(1)
            user = mUser
        }

    companion object {
        val instance = SharedPreferencesManager()
    }
}