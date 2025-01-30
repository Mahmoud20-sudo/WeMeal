package com.wemeal.data.model

import com.wemeal.presentation.util.Event

data class AuthUiModel(
    val showProgress: Boolean,
    val error: Event<Pair<AuthType, MaterialDialogContent>>?,
    val success: Boolean,
//    val showAllLinkProvider: Event<Pair<AuthType, MaterialDialogContent>>?,
    val accessToken: String?
)