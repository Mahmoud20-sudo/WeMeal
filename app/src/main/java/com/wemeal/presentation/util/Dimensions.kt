package com.wemeal.presentation.util

import android.util.Log
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Dimensions {
    object Width : Dimensions()
    object Height : Dimensions()

    sealed class DimensionsOperator {
        object LessThan : DimensionsOperator()
        object GreaterThan : DimensionsOperator()
    }

    class DimensionsComparator(
        val operator: DimensionsOperator,
        private val dimensions: Dimensions,
        val value: Dp
    ) {

        fun compare(screenWidth: Dp, screenHeight: Dp): Boolean {
            return if (dimensions is Width) {
                when (operator) {
                    is DimensionsOperator.LessThan -> screenWidth < value
                    is DimensionsOperator.GreaterThan -> screenWidth > value
                }
            } else {
                when (operator) {
                    is DimensionsOperator.LessThan -> screenHeight < value
                    is DimensionsOperator.GreaterThan -> screenHeight > value
                }
            }
        }
    }
}

@Composable
fun MediaQuery(comparator: Dimensions.DimensionsComparator, content: @Composable () -> Unit) {
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density
    val screenHeight =
        LocalContext.current.resources.displayMetrics.heightPixels.dp / LocalDensity.current.density

    if (comparator.compare(screenWidth = screenWidth, screenHeight = screenHeight))
        content()
}

@Composable
fun MediaQuery(content: @Composable (width: Dp) -> Unit) {
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density
    content(screenWidth)
}

infix fun Dimensions.lessThan(value: Dp): Dimensions.DimensionsComparator {
    return Dimensions.DimensionsComparator(
        operator = Dimensions.DimensionsOperator.LessThan,
        dimensions = this,
        value = value
    )
}

infix fun Dimensions.greaterThan(value: Dp): Dimensions.DimensionsComparator {
    return Dimensions.DimensionsComparator(
        operator = Dimensions.DimensionsOperator.GreaterThan,
        dimensions = this,
        value = value
    )
}
