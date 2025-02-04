package com.buller.ckkal.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.saved.roller.NumericRoller
import com.buller.ckkal.ui.screens.states.DishState


@Composable
fun CalculationRoute(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSavedDishesNav: () -> Unit
) {
    val state by sharedViewModel.dish.collectAsStateWithLifecycle()
    CalculationScreen(
        modifier = modifier,
        state = state,
        onBack = {
            sharedViewModel.cleanDish()
            onBack.invoke()
        },
        onSaveDishes = {
            sharedViewModel.saveDish()
            onSavedDishesNav.invoke()
        })
}

@Composable
fun CalculationScreen(
    modifier: Modifier = Modifier,
    state: DishState,
    onBack: () -> Unit,
    onSaveDishes: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NutrientCircle(state.finalKcal, stringResource(R.string.kkal), 200.dp)
            Row {
                NutrientCircle(state.finalProteins, stringResource(R.string.proteins), 72.dp)
                NutrientCircle(state.finalFats, stringResource(R.string.fats), 72.dp)
                NutrientCircle(state.finalCarbs, stringResource(R.string.carbs), 72.dp)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray.copy(alpha = 0.3f)
            )
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    CopyButton(
                        text = stringResource(R.string.kkal),
                        modifier = Modifier.weight(1f),
                        onCopy = {
                            clipboardManager.setText(AnnotatedString("${state.finalKcal}"))
                        })
                    CopyButton(
                        text = stringResource(R.string.proteins),
                        modifier = Modifier.weight(1f),
                        onCopy = {
                            clipboardManager.setText(AnnotatedString("${state.finalProteins}"))
                        })
                    CopyButton(
                        text = stringResource(R.string.fats),
                        modifier = Modifier.weight(1f),
                        onCopy = {
                            clipboardManager.setText(AnnotatedString("${state.finalFats}"))
                        })
                    CopyButton(
                        text = stringResource(R.string.carbs),
                        modifier = Modifier.weight(1f),
                        onCopy = {
                            clipboardManager.setText(AnnotatedString("${state.finalCarbs}"))
                        })
                }
                Text(
                    text = stringResource(R.string.copy_nutrient_title),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(60.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = LocalContentColor.current
                )
                Text(text = stringResource(R.string.delete))
            }
            Button(
                onClick = onSaveDishes,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Rounded.Done,
                    contentDescription = stringResource(R.string.save),
                    tint = LocalContentColor.current
                )
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

@Composable
fun NutrientCircle(
    value: Double,
    label: String,
    size: Dp = 24.dp,
    color: Color = Color.LightGray.copy(alpha = 0.3f)
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        Surface(
            shape = CircleShape,
            color = color,
            modifier = Modifier.size(size)
        ) {
            Box(contentAlignment = Alignment.Center) {
                NumericRoller(number = value, isNeedToStartZero = true)
            }
        }
        Text(text = label, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun CopyButton(
    text: String,
    modifier: Modifier = Modifier,
    onCopy: () -> Unit
) {
    Button(
        onClick = onCopy,
        modifier = modifier.padding(end = 4.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculationScreenPreview() {
    val dish = DishState(
        finalProteins = 1.0,
        finalKcal = 1.0,
        finalFats = 1.0,
        finalCarbs = 1.0,
    )
    val state = remember { dish }
    CalculationScreen(state = state, onBack = {}, onSaveDishes = {})
}
