package com.wemeal.presentation.shared

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wemeal.presentation.onboarding.viewmodel.OnBoardingViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modifier: Modifier,
    bottomState: ModalBottomSheetState,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    sheetContent: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = bottomState,
        modifier = modifier,
        sheetShape = shape,
        scrimColor = Color.Transparent,
        sheetContent = { sheetContent() }) {}
}
