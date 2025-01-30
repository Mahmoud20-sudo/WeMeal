package com.wemeal.presentation.main.components.createpost.tagged

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wemeal.presentation.intro.grey
import com.wemeal.presentation.intro.purple600
import com.wemeal.presentation.intro.purple800
import com.wemeal.presentation.util.montserratMedium

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewMessageBox(){
    MessageBox("TEEES", 10.dp)
}

@ExperimentalAnimationApi
@Composable
fun MessageBox(message: String?, height: Dp, showDivider: Boolean = false) {
    Column {
        Box(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 11.5.dp)
                .background(color = purple800.copy(alpha = 0.15f))
                .fillMaxWidth()
                .height(height = height),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message ?: "-",
                maxLines = 1,
                color = purple600,
                fontSize = 12.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(montserratMedium)
                )
            )
        }
        AnimatedVisibility(visible = showDivider) {
            Spacer(
                modifier = Modifier
                    .padding(top = 2.5.dp, bottom = 8.5.dp)
                    .fillMaxWidth()
                    .background(grey.copy(alpha = 0.3f))
                    .height(1.dp)

            )
        }
    }

}