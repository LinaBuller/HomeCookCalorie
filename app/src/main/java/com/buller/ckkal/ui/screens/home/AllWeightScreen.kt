package com.buller.ckkal.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.views.CustomOutlinedTextField
import com.buller.ckkal.ui.screens.views.DualButtonPanel
import com.buller.ckkal.ui.screens.views.LEFT_BUTTON
import com.buller.ckkal.ui.screens.views.RIGHT_BUTTON
import com.buller.ckkal.ui.screens.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AllWeightRoute(
    oldWeight: Double?,
    bottomBarHeight: Dp = 0.dp,
    viewModel: SharedViewModel,
    navigateCalculateScreen: () -> Unit,
    onUpdateDish: (Double) -> Unit,
    onBack: () -> Unit,
) {
    val isEdit = oldWeight != null
    AllWeightScreen(
        oldWeight = oldWeight,
        bottomBarHeight = bottomBarHeight,
        onAddAllWeight = { weight ->
            if (isEdit) {
                onUpdateDish(weight)
            } else {
                viewModel.setTotalWeight(weight)
                CoroutineScope(Dispatchers.Main).launch {
                    delay(300)
                    viewModel.calculateCurrentDish()
                }
                navigateCalculateScreen.invoke()
            }
        },
        onBack = onBack,
    )
}

@Composable
fun AllWeightScreen(
    oldWeight: Double? = null,
    bottomBarHeight: Dp,
    onBack: () -> Unit,
    onAddAllWeight: (Double) -> Unit,
) {
    WeightForm(
        oldWeight = oldWeight,
        bottomBarHeight = bottomBarHeight,
        onBack = onBack,
        onAddAllWeight = onAddAllWeight
    )
}

@Composable
fun WeightForm(
    oldWeight: Double? = null,
    bottomBarHeight: Dp = 0.dp,
    onAddAllWeight: (Double) -> Unit,
    onBack: () -> Unit,
) {
    var weightText by remember {
        mutableStateOf(
            if (oldWeight == null) {
                TextFieldValue(
                    text = "",
                    selection = TextRange(0)
                )
            } else {
                TextFieldValue(
                    text = oldWeight.toString(),
                    selection = TextRange(oldWeight.toString().length)
                )
            }

        )
    }
    var isError by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    fun handleSubmit() {
        val weight = weightText.text.toDoubleOrNull()
        if (weight != null && weight > 0.0) {
            isError = false
            onAddAllWeight(weight)
        } else {
            isError = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = weightText,
                onValueChange = { updated ->
                    weightText = updated
                    if (isError) isError = false
                },
                label = stringResource(R.string.all_weight),
                isNumeric = true,
                onNext = {
                    if (oldWeight != null) handleSubmit()
                },
                onShowError = {},
                maxLength = 10,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .focusRequester(focusRequester)
            )
        }

        val imeBottomPadding = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
        val safeBottomPadding = (imeBottomPadding - bottomBarHeight).coerceAtLeast(0.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = safeBottomPadding
                )
        ) {
            DualButtonPanel(
                leftButtonText = R.string.cancel,
                leftButtonIcon = Icons.Default.Clear,
                rightButtonIcon = Icons.Default.Check,
                rightButtonText = if (oldWeight != null) {
                    R.string.save
                } else {
                    R.string.calculate
                },
                enabledRightButton = weightText.text.isNotBlank(),
                onClickButton = { button ->
                    when (button) {
                        LEFT_BUTTON -> onBack.invoke()
                        RIGHT_BUTTON -> handleSubmit()
                    }
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllWeightDishDialogPreview() {
    WeightForm(onBack = {}, onAddAllWeight = {})
}