package com.wemeal.presentation.intro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.wemeal.presentation.intro.googleColor

@ExperimentalUnitApi
@Preview(name = "Title")
@androidx.compose.runtime.Composable
fun previewButtonRounded() {
    ButtonRounded(
        onClick = {},
        text = "Hola Ann",
        idDrawableRes = com.wemeal.R.drawable.ic_facbook_icon,
        color = googleColor
    )
}

@ExperimentalUnitApi
@androidx.compose.runtime.Composable
fun ButtonRounded(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    idDrawableRes: Int,
    color: Color
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        shape = RoundedCornerShape(50),
        modifier = modifier,
        elevation = ButtonDefaults.elevation(1.dp),
    ) {

        Row(
            Modifier
                .fillMaxSize().padding(start = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painterResource(idDrawableRes),
                contentDescription = "",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
            )

            Text(
                text = text,
                color = color,
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                letterSpacing = TextUnit(0.22f, TextUnitType.Sp),
                modifier = Modifier.padding(start = 20.dp)
            )
        }


    }
}