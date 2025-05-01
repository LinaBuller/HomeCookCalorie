package com.buller.ckkal.ui.screens.roller

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun DigitRoller(
    modifier: Modifier = Modifier,
    digit: Char,
    canAnimate: Boolean,
) {
    val wheelScrollState = rememberWheelScrollState()

    LaunchedEffect(key1 = digit, key2 = canAnimate) {
        val index = getDigitIndex(digit)
        if (canAnimate) {
            wheelScrollState.animateScrollToIndex(index)
        } else {
            wheelScrollState.scrollToIndex(index)
        }
    }

    Layout(measurePolicy = { measurables, constraints ->
        val placeableList = measurables.map {
            it.measure(constraints)
        }

        val maxMeasuredHeight = placeableList.map { it.height }.max()
        val maxMeasuredWidth = placeableList.map { it.width }.max()
        wheelScrollState.updateItemHeight(maxMeasuredHeight)

        layout(maxMeasuredWidth, maxMeasuredHeight) {
            var height = wheelScrollState.value
            placeableList.forEach {placeable->
                placeable.placeRelative(
                    (maxMeasuredWidth - placeable.width) / 2,
                    height + ((maxMeasuredHeight - placeable.height) / 2)
                )
                height += maxMeasuredHeight
            }
        }
    }, content = {
        getWheelItems().forEach { character ->
            Text(
                text = character,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 26.sp,
                modifier = Modifier.padding(1.dp)
            )
        }
    }, modifier = modifier.clipToBounds())
}

private fun getWheelItems(): List<String> {
    return listOf("", ".", *(0..9).map { it.toString() }.toTypedArray())
}

private fun getDigitIndex(digit: Char): Int {
    return getWheelItems().indexOfFirst { it == digit.toString() }
}

@Preview(showBackground = true)
@Composable
private fun DigitWheelPreview() {
    DigitRoller(digit = '8', canAnimate = false)
}