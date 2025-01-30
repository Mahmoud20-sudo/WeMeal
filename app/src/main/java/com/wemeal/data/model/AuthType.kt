package com.wemeal.data.model

fun String.toAuthType(): AuthType {
    return when (this) {
        AuthType.FACEBOOK.authValue -> AuthType.FACEBOOK
        else -> AuthType.GOOGLE
    }
}

enum class AuthType(var authValue: String) {
    FACEBOOK("facebook.com"),
    GOOGLE("google.com"),
}