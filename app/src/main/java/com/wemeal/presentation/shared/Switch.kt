package com.wemeal.presentation.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.presentation.intro.*
import com.wemeal.presentation.intro.purple500
import com.wemeal.presentation.util.montserratRegular
import com.wemeal.presentation.util.montserratSemiBold

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewSwitchDemo() {
    SwitchDemo("TEST", mutableStateOf(false))
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SwitchDemo(
    title: String,
    checkedState: MutableState<Boolean>
) {

    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = purple500,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = dimmedColor100,
                checkedTrackColor = purple150
            )
        )
        Text(
            text = title,
            color = darkGrey,
            fontSize = 12.sp,
            maxLines = 1,
            style = TextStyle(
                fontFamily = FontFamily(montserratRegular)
            ),
            modifier = Modifier.padding(start = 8.dp),
            letterSpacing = TextUnit(-0.42f, TextUnitType.Sp)
        )
    }
}