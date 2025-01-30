package com.wemeal.presentation.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle

fun Context.launch(
    options: Bundle? = null,
    intent: Intent
) = startActivity(intent, options)
