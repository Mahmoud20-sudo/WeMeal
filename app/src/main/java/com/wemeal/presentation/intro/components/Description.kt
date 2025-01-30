package com.wemeal.presentation.intro.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wemeal.presentation.intro.regularBlue

@Preview(name = "Description")
@Composable
fun previewDescription() {
    Description(text = "")
}

@Composable
fun Description(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = regularBlue,
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}