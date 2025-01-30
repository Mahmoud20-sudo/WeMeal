package com.wemeal.presentation.onboarding.components.header

import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatEditText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.wemeal.R
import com.google.android.material.textfield.TextInputLayout
import com.wemeal.data.model.user.PlaceDataModel
import com.wemeal.presentation.extensions.*
import com.wemeal.presentation.shared.ClickableImage
import com.wemeal.presentation.util.ImageActions
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
fun TextFieldWithDropDown(
    modifier: Modifier = Modifier,
    searchResult: SnapshotStateList<PlaceDataModel>?,
    isSearching: MutableState<Boolean>,
    querySearch: MutableState<String>
) {
    val keyboardController = LocalSoftwareKeyboardController.current
//    val currentContext = LocalContext.current
    val isCanceled = remember { mutableStateOf(false) }
    var job: Job? = null

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                val textInputLayout = TextInputLayout
                    .inflate(context, R.layout.autocomplete_layout, null) as TextInputLayout
                val autoCompleteTextView = textInputLayout.editText as? AppCompatEditText
                val typeface: Typeface? =
                    ResourcesCompat.getFont(context, R.font.montserrat_regular)
                autoCompleteTextView?.typeface = typeface

                autoCompleteTextView?.afterTextChanged { query ->
                    querySearch.value = query
                    job?.cancel()
//                    context.activity()?.logEvent(CustomEvent.USER_SEARCH_LOCATION, EventCase.ATTEMPT)
                    job = CoroutineScope(IO).launch {
                        kotlin.runCatching {
                            searchResult?.clear()
                            delay(1000)
                            searchResult?.addAll(getAutocomplete(context, query))
                        }
                    }
                }

//                autoCompleteTextView?.setOnEditorActionListener(OnEditorActionListener { _, actionId, event ->
//                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                        return@OnEditorActionListener true
//                    }
//                    false
//                })

                autoCompleteTextView?.setOnClickListener {
                    isSearching.value = true
                }
                autoCompleteTextView?.setOnFocusChangeListener { _, isFocused ->
                    isSearching.value = isFocused
                    context.activity()
                        ?.logEvent(CustomEvent.USER_SEARCH_LOCATION_TYPING, EventCase.ATTEMPT)
                }
                textInputLayout
            },
            update = { textInputLayout ->
                val autoCompleteTextView = textInputLayout.editText as? AppCompatEditText
                if (!isSearching.value) {
                    autoCompleteTextView?.clearFocus()
                    autoCompleteTextView?.text?.clear()
                    keyboardController?.hide()
                    querySearch.value = ""
                    searchResult?.clear()
                }
                if (isCanceled.value) {
//                    autoCompleteTextView?.clearFocus()
                    autoCompleteTextView?.text?.clear()
                    keyboardController?.hide()
                    searchResult?.clear()
                    isCanceled.value = false
                }
            },
            modifier = modifier.align(alignment = Alignment.CenterStart)
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd),
            visible = isSearching.value
        ) {
            ClickableImage(
                drawableId = R.drawable.ic_close_circle,
                imageActions = ImageActions.CLOSE,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            ) {
                isCanceled.value = true
            }
        }
    }
}
