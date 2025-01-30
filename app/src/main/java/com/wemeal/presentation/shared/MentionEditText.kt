package com.wemeal.presentation.shared

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
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

@SuppressLint("UnrememberedMutableState")
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Preview
@Composable
fun PreviewMentionEditText() {
    MentionEditText(
        modifier = Modifier,
        postText = mutableStateOf(""),
        isMentionText = mutableStateOf(false),
        loading = false,
        usersList = mutableListOf(),
        isFocused = false,
        searchQuery = "",
        scrollState = rememberScrollState(),
        usersMap = mutableMapOf(),
//        originalPostText = mutableStateOf("")
    )
}

//////////////////////////////////////////////
@ExperimentalCoilApi
@ExperimentalUnitApi
@ExperimentalAnimationApi
@Composable
fun MentionEditText(
    modifier: Modifier,
    postText: MutableState<String>,
    isMentionText: MutableState<Boolean>,
    isFocused: Boolean,
    loading: Boolean,
    usersList: MutableList<Result>?,
    searchQuery: String,
    scrollState: ScrollState,
    usersMap: MutableMap<Pattern, Result>?,
//    originalPostText: MutableState<String>
) {
    var mentionEd: WeakReference<MentionEditText>? = null
//    val postText = rememberSaveable { mutableStateOf("") }
    var offsetY by remember { mutableStateOf(0f) }
    var sympolPosition by remember { mutableStateOf(-1) }
    val mentionsList = mutableListOf<Result>()
    val coroutineScope = rememberCoroutineScope()
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density

    val popupHeight = when (usersList?.size) {
        1 -> 50.dp
        2 -> 100.dp
        3 -> 150.dp
        4 -> 200.dp
        else -> 250.dp
    }

    LaunchedEffect(isFocused) {
        if (isFocused)
            mentionEd?.get()?.showKeyboard()
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (androidView, mentionsPopup) = createRefs()

        AndroidView(
            factory = { context ->
                val mentionEditText = LayoutInflater.from(context)
                    .inflate(R.layout.mention_layout, null, false) as MentionEditText

//                val typeface: Typeface? =
//                    ResourcesCompat.getFont(context, R.font.montserrat_regular)
//                mentionEditText.typeface = typeface

                mentionEditText.afterTextChanged { query ->
                    postText.value = query
                    sympolPosition = postText.value.indexOf("@\n")
                    offsetY = mentionEditText.currentCursorLine
                    if (postText.value.isNotEmpty() && postText.value[postText.value.length - 1] == '\n')
                        coroutineScope.launch {
//                            Log.e("AAAAAA", mentionEditText.bottom.toString())
                            scrollState.scrollTo(mentionEditText.bottom)
                        }
                }

                mentionEditText.setMentionTextColor(Color.parseColor("#FF8A50DE")) //optional, set highlight color of mention string
//                mentionEditText.setPatterns("@[\\u4e00-\\u9fa5\\w\\-]+")
                mentionEditText.setOnMentionInputListener(object :
                    MentionEditText.OnMentionInputListener {
                    override fun onMentionCharacterInput(isMention: Boolean) {
                        //call when '@' character is inserted into EditText
                        isMentionText.value = isMention
                    }
                })

                mentionEditText
            },
            update = { mentionEditText ->
                val mentionEdT = mentionEditText as? MentionEditText
                mentionEd = WeakReference(mentionEdT)
            },
            modifier = Modifier
                .constrainAs(androidView) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
        )

        if (isMentionText.value)
            Box(
                modifier = Modifier
                    .padding(top = 26.dp)
                    .offset(0.dp, offsetY.dp)
                    .constrainAs(mentionsPopup) {
                        top.linkTo(androidView.top)
                        start.linkTo(parent.start)
                    }
            ) {

                Popup(
                    properties = PopupProperties(focusable = false),
                    onDismissRequest = {
                        isMentionText.value = false
                    }) {

                    Card(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .background(White, RoundedCornerShape(8.dp))
                            .padding(start = 16.dp, end = 16.dp)
                    ) {

                        MentionsWindow(
                            modifier = Modifier,
                            loading = loading,
                            popupHeight = popupHeight,
                            usersList = usersList
                        ) { index ->
                            if (loading || usersList?.isNullOrEmpty() == true) return@MentionsWindow
                            postText.value = postText.value.replace("@$searchQuery", "")

                            val userFullName =
                                "${usersList[index].firstName} ${usersList[index].lastName} "

                            when (sympolPosition) {
                                -1 -> {
                                    postText.value += userFullName
//                            originalPostText.value += usersList?.get(index)?.username
                                }
                                else -> {
                                    val sb = StringBuilder(postText.value)
                                    sb.insert(
                                        sympolPosition,
                                        userFullName
                                    )
                                    postText.value = sb.toString()
                                    mentionEd?.get()
                                        ?.setCursorPosition(
                                            postText.value.indexOf(userFullName) + userFullName.length
                                        )
                                    sympolPosition = -1
                                }
                            }

                            mentionsList.add(usersList.get(index))
//                    hashMap[Pattern.compile(userFullName)] = usersList[index]
                            usersMap?.put(Pattern.compile(userFullName), usersList.get(index))
//                    mentionEd?.get()?.setUserMap(usersMap)
                            mentionEd?.get()?.setPatterns(usersMap)
                            mentionEd?.get()?.getMentionList(true)?.addAll(mentionsList)
                            mentionEd?.get()?.setText(postText.value, TextView.BufferType.SPANNABLE)
                            isMentionText.value = false
                        }
                    }
                }
            }
    }
}