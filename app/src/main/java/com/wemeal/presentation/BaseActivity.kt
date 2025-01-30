package com.wemeal.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.wemeal.R
import com.wemeal.presentation.extensions.isOnline
import com.wemeal.presentation.extensions.notInternetToast
import com.wemeal.presentation.extensions.shortToast

@SuppressLint("Registered")
open class BaseActivity(
) : AppCompatActivity() {

    protected open val isFullscreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFullscreen) setFullScreenWindow()
    }

    protected fun <T, LD : LiveData<T>> observe(liveData: LD, onChanged: (T) -> Unit) {
        liveData.observe(this) {
            it?.let(onChanged)
        }
    }

    private fun setFullScreenWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    fun fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    fun hideSystemUI() {
        if (Build.VERSION.SDK_INT in 12..18) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or 0
            decorView.systemUiVisibility = uiOptions
        }
    }

    fun changeStatusBarIconsColor(shouldChangeStatusBarTintToDark: Boolean, color: Int) {
        window.statusBarColor = getColor(color)
        val decor = window.decorView
        if (shouldChangeStatusBarTintToDark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            // We want to change tint color to white again.
            // You can also record the flags in advance so that you can turn UI back completely if
            // you have set other flags before, such as translucent or full screen.
            decor.systemUiVisibility = 0
        }
    }

    fun checkInternetConnection() {
        if (!isOnline(context = this))
            notInternetToast()
    }
}