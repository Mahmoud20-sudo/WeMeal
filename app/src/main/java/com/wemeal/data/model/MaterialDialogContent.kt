package com.wemeal.data.model

import androidx.annotation.StringRes

data class MaterialDialogContent(
    val positiveText: String?,
    val content: String? = null,
    val title: String?,
    val negativeText: String? = null,
    val contentText: String? = null
)