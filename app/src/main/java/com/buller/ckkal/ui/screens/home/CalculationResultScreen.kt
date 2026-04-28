package com.buller.ckkal.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.views.CustomOutlinedTextField
import com.buller.ckkal.ui.screens.views.DualButtonPanel
import com.buller.ckkal.ui.screens.views.LEFT_BUTTON
import com.buller.ckkal.ui.screens.views.RIGHT_BUTTON
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.home.views.NutrientPart
import com.buller.ckkal.ui.screens.states.DishState

@Composable
fun CalculationResultRoute(
    viewModel: SharedViewModel,
    bottomBarHeight: Dp = 0.dp,
    navigateToHomeScreen: () -> Unit,
    navigateToSavedDishes: () -> Unit,
) {
    val state = viewModel.dish.collectAsStateWithLifecycle()

    val onSaveDish = { dishName: String ->
        viewModel.setDishName(dishName)
        viewModel.saveDish()
        navigateToSavedDishes.invoke()
    }
    CalculationResultScreen(
        state = state.value,
        bottomBarHeight = bottomBarHeight,
        onSaveDishes = onSaveDish,
        onCalculateCancel = navigateToHomeScreen
    )
}

@Composable
fun CalculationResultScreen(
    state: DishState,
    bottomBarHeight: Dp = 0.dp,
    onSaveDishes: (String) -> Unit,
    onCalculateCancel: () -> Unit
) {
    var dishName by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            NutrientPart(state = state)
        }
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
        ) {
            CustomOutlinedTextField(
                value = dishName,
                onValueChange = { dishName = it },
                label = stringResource(R.string.dish_name),
                modifier = Modifier.fillMaxWidth(),
                maxLength = 40,
                onNext = {}
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        val imeBottomPadding = WindowInsets.ime
            .asPaddingValues()
            .calculateBottomPadding()
        val safeBottomPadding = (imeBottomPadding - bottomBarHeight).coerceAtLeast(0.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = safeBottomPadding
                )
        ) {
            DualButtonPanel(
                leftButtonIcon = Icons.Default.Clear,
                leftButtonText = R.string.cancel,
                rightButtonIcon = Icons.Rounded.Done,
                rightButtonText = R.string.save,
                onClickButton = { button ->
                    when (button) {
                        LEFT_BUTTON -> {
                            onCalculateCancel.invoke()
                        }

                        RIGHT_BUTTON -> {
                            onSaveDishes(dishName)
                        }
                    }
                }
            )
        }
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
    CalculationResultScreen(onSaveDishes = {}, state = state, onCalculateCancel = {})
}