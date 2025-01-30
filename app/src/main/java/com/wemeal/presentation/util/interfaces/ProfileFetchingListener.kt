package com.wemeal.presentation.util.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface ProfileFetchingListener{
    fun create(googleSignInAccount: GoogleSignInAccount)
}