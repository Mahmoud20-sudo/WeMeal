package com.wemeal.presentation.main.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wemeal.R
import com.wemeal.presentation.extensions.loggingPostAction
import com.wemeal.presentation.extensions.setBackStackData
import com.wemeal.presentation.extensions.shortToast
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.main.viewmodel.MainViewModel
import com.wemeal.presentation.onboarding.components.text.InputEditText
import com.wemeal.presentation.shared.ClickableText
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import com.wemeal.presentation.util.montserratMedium
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewReportScreen() {
    ReportScreen(
        rememberNavController(), id = "", viewModel = null
    )
}

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReportScreen(navController: NavController, id: String, viewModel: MainViewModel?) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 18.dp, end = 18.dp, top = 15.dp, bottom = 3.dp)
            .verticalScroll(rememberScrollState())
    ) {

        var typeing by remember { mutableStateOf(false) }
        var other by remember { mutableStateOf(false) }
        var enabled by remember { mutableStateOf(false) }
        var selected by remember { mutableStateOf("Android") }
        val radioGroupOptions = context.resources.getStringArray(R.array.report_items)
        var elseText by rememberSaveable { mutableStateOf("") }

        val onSelectedChange = { text: String ->
            selected = text
            enabled = true
            typeing = false
            elseText = ""
        }

        Text(
            text = stringResource(id = R.string.report_brief),
            color = black300,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(18f, TextUnitType.Sp),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(montserratMedium)
            )
        )

        radioGroupOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 23.dp)
//                .selectable(
//                    selected = (text == selected),
//                    onClick = {
//                        other = text == radioGroupOptions[radioGroupOptions.size - 1]
//                        onSelectedChange(text)
//                    }
                , verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selected),
                    onClick = {
                        other = text == radioGroupOptions[radioGroupOptions.size - 1]
                        onSelectedChange(text)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = purple600
                    )
                )
                Text(
                    text = text,
                    color = black300,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    letterSpacing = TextUnit(-0.02f, TextUnitType.Sp),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratRegular)
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(top = 23.dp)
                .background(Color.White)
                .fillMaxWidth()
                .wrapContentHeight(), visible = other
        ) {
            Column {
                InputEditText(
                    value = elseText,
                    onValueChange = {
                        typeing = true
                        if (it.length <= 300) {
                            elseText = it
                        }
                    },
                    placeHolderString = stringResource(id = R.string.tell_us_more),
                    modifier = Modifier,
                    maxLines = 1,
                    contentTextStyle = TextStyle(
                        fontSize = 16.sp,
                        color = black500,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratSemiBold)
                    ),
                    hintTextStyle = TextStyle(
                        color = lightGrey200,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(montserratMedium)
                    ),
                    cursorColor = purple600,
                    singleLine = true
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 36.dp)
                        .fillMaxWidth()
                        .height(if (typeing) 1.dp else 1.dp)
                        .background(if (typeing) purple600 else lightGrey100)
                )
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        ClickableText(
            text = stringResource(id = R.string.submit),
            color = Color.White,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = FontFamily(montserratSemiBold)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(41.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            blue300,
                            purple
                        )
                    ),
                    shape = RoundedCornerShape(20.dp),
                    alpha = if (enabled) 0.7f else 0.35f
                )
                .padding(top = 11.dp)
        ) {
            if (enabled) {
                if (other && elseText.length < 20) {
                    context.shortToast(context.getString(R.string.empty_report))
                    return@ClickableText
                }
                viewModel?.reportText?.value = selected
                setBackStackData(navController, id)
                navController.navigateUp()
                loggingPostAction(
                    context = context,
                    model = viewModel?.postPressed,
                    eventName =  CustomEvent.USER_SUBMIT_REPORT_POST,
                    eventCase = EventCase.SUCCESS
                )
            }
        }
    }
}