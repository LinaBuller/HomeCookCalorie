package com.buller.ckkal.ui.screens.home.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.ColorPairs
import com.buller.ckkal.ui.screens.roller.NumericRoller
import com.buller.ckkal.ui.screens.states.DishState

@Composable
fun NutrientPart(modifier: Modifier = Modifier, state: DishState) {
    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NutrientCircle(
                value = state.finalKcal,
                color = ColorPairs.getCaloriePairColor(),
                label = stringResource(R.string.kkal),
                size = 144.dp,
                onClick = {
                    clipboardManager.setText(AnnotatedString("${state.finalKcal}"))
                }
            )
            Row {
                NutrientCircle(
                    value = state.finalProteins,
                    color = ColorPairs.getProteinsPairColor(),
                    label = stringResource(R.string.proteins),
                    size = 72.dp,
                    onClick = {
                        clipboardManager.setText(AnnotatedString("${state.finalProteins}"))
                    }
                )
                NutrientCircle(
                    value = state.finalFats,
                    color = ColorPairs.getFatsPairColor(),
                    label = stringResource(R.string.fats),
                    size = 72.dp,
                    onClick = {
                        clipboardManager.setText(AnnotatedString("${state.finalFats}"))
                    }
                )
                NutrientCircle(
                    value = state.finalCarbs,
                    color = ColorPairs.getCarbohydratesPairColor(),
                    label = stringResource(R.string.carbs),
                    size = 72.dp,
                    onClick = {
                        clipboardManager.setText(AnnotatedString("${state.finalCarbs}"))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.copy_nutrient_title),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NutrientCircle(
    value: Double,
    label: String,
    size: Dp = 24.dp,
    color: Pair<Color, Color>,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Surface(
            shape = CircleShape, color = color.second, modifier = Modifier.size(size).clickable(onClick = onClick)
        ) {
            Box(contentAlignment = Alignment.Center) {
                NumericRoller(number = value, isNeedToStartZero = true)
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable(onClick = onClick)
        )
    }
}