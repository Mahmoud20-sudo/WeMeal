package com.wemeal.presentation.shared

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.annotation.ExperimentalCoilApi
import com.wemeal.R
import com.wemeal.data.model.main.Result
import com.wemeal.presentation.extensions.afterTextChanged
import com.wemeal.presentation.extensions.showKeyboard
import com.wemeal.presentation.main.components.createpost.MentionsWindow
import com.wemeal.presentation.util.MentionEditText
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.lang.ref.WeakReference
import java.util.regex.Pattern
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wemeal.presentation.main.GalleryAdapter


@Preview
@Composable
fun PreviewStaggeredGrid() {
    StaggeredGrid(
        modifier = Modifier,
        mutableListOf()
    )
}

@Composable
fun StaggeredGrid(
    modifier: Modifier,
    galleryList: MutableList<String>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {

        val adapter = GalleryAdapter {

        }

        AndroidView(
            factory = { context ->
                val recyclerView = LayoutInflater.from(context)
                    .inflate(R.layout.recyclerview_layout, null, false) as RecyclerView

                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

                recyclerView.layoutManager =
                    staggeredGridLayoutManager // set LayoutManager to RecyclerView

//                recyclerView.adapter = adapter
                recyclerView
            },
            update = {
                adapter.diffResult.submitList(galleryList)
                it.isNestedScrollingEnabled = false
                it.adapter = adapter
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}