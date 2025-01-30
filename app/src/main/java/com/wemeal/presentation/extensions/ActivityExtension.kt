package com.wemeal.presentation.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.launch(
    requestCode: Int = -1,
    options: Bundle? = null,
    intent: Intent
) = startActivityForResult(intent, requestCode)