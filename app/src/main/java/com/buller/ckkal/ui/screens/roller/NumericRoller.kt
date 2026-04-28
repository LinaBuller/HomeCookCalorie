package com.buller.ckkal.ui.screens.roller


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun NumericRoller(
    modifier: Modifier = Modifier,
    number: Double,
    isNeedToStartZero: Boolean = false,
) {
    val animationTracker = remember { AnimationTracker() }
    val displayedNumber by rememberUpdatedState(
        if (isNeedToStartZero) 0.0 else number
    )

    LaunchedEffect(number) {
        animationTracker.numberOfTrackedInputChanges++
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(modifier = modifier.animateContentSize(getSizeChangeAnimationSpec())) {

            val displayedDigitsArray = generateDigitArray(
                if (isNeedToStartZero) number else displayedNumber
            )

            displayedDigitsArray.forEach { character ->
                DigitRoller(
                    digit = character,
                    canAnimate = animationTracker.canAnimate,
                )
            }
        }
    }
}

fun getSizeChangeAnimationSpec(): FiniteAnimationSpec<IntSize> {
    return tween(2000, easing = EaseOutExpo)
}

@Composable
private fun generateDigitArray(number: Double): CharArray {
    val rawDigitsArray = number.toString().toCharArray().reversedArray()
    return rawDigitsArray
}

@Preview(showBackground = true)
@Composable
private fun NumericRollerPreview() {
    NumericRoller(
        number = 2300.0,
        isNeedToStartZero = false
    )
}