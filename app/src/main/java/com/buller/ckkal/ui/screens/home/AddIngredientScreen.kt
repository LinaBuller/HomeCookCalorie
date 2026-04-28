package com.buller.ckkal.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.domain.utils.MathUtil
import com.buller.ckkal.ui.screens.views.CustomOutlinedTextField
import com.buller.ckkal.ui.screens.views.DualButtonPanel
import com.buller.ckkal.ui.screens.views.LEFT_BUTTON
import com.buller.ckkal.ui.screens.views.RIGHT_BUTTON
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.roller.NumericRoller
import com.buller.ckkal.ui.screens.states.IngredientsState
import kotlinx.coroutines.delay

@Composable
fun AddIngredientRoute(
    isEditing: Boolean = false,
    viewModel: SharedViewModel = hiltViewModel(),
    nutrientsFormViewModel: NutrientsFormViewModel = hiltViewModel(),
    bottomBarHeight: Dp,
    navigateToAddTotalWeightScreen: () -> Unit,
    onUpdateDish: (Ingredient) -> Unit,
    onBack: () -> Unit = {},
) {
    LaunchedEffect(nutrientsFormViewModel.ingredientAdded) {
        nutrientsFormViewModel.ingredientAdded.collect { ingredient ->
            if (isEditing) {
                onUpdateDish.invoke(ingredient)
            } else {
                viewModel.setIngredient(ingredient)
            }
        }
    }

    LaunchedEffect(nutrientsFormViewModel.navigationEvent) {
        nutrientsFormViewModel.navigationEvent.collect { event ->
            when (event) {
                NavigationEvent.NavigateToAddTotalWeight -> navigateToAddTotalWeightScreen.invoke()
                NavigationEvent.NavigateToBack -> onBack.invoke()
            }
        }
    }

    val state by viewModel.ingredientsState.collectAsStateWithLifecycle()

    AddIngredientScreen(
        state = state,
        nutrientsFormViewModel = nutrientsFormViewModel,
        isEditing = isEditing,
        bottomBarHeight = bottomBarHeight
    )
}

@Composable
fun AddIngredientScreen(
    state: IngredientsState,
    nutrientsFormViewModel: NutrientsFormViewModel,
    isEditing: Boolean = false,
    bottomBarHeight: Dp,
) {
    var showAddedPreview by remember { mutableStateOf(false) }

    LaunchedEffect(nutrientsFormViewModel.ingredientAdded) {
        nutrientsFormViewModel.ingredientAdded.collect {
            showAddedPreview = true
            delay(600)
            showAddedPreview = false
        }
    }

    AnimatedVisibility(
        visible = showAddedPreview,
        enter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(400)),
        exit = slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(200))
    ) {
        Box {
            IngredientList(
                listOfIngredients = state.ingredients,
                onDeleteIngredient = {},
                onEditIngredient = {}
            )
        }
    }

    AnimatedVisibility(
        visible = !showAddedPreview,
        enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(400)),
        exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(200))
    ) {
        NutrientsForm(
            isFirstIngredient = state.ingredients.isNotEmpty(),
            viewModel = nutrientsFormViewModel,
            isEditing = isEditing,
            bottomBarHeight = bottomBarHeight
        )
    }
}


@Composable
fun NutrientsForm(
    viewModel: NutrientsFormViewModel,
    isFirstIngredient: Boolean = false,
    isEditing: Boolean = false,
    bottomBarHeight: Dp,
) {
    val uiState by viewModel.uiState.collectAsState()

    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterFats = remember { FocusRequester() }
    val focusRequesterCarbs = remember { FocusRequester() }
    val focusRequesterProteins = remember { FocusRequester() }
    val focusRequesterKkal = remember { FocusRequester() }
    val focusRequesterWeight = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    LaunchedEffect(focusRequesterName) {
        focusRequesterName.requestFocus()
        keyboardController?.show()
    }


    Column(
        modifier =  Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(16.dp),
        ) {
            CustomOutlinedTextField(
                value = uiState.ingredientName,
                onValueChange = { viewModel.onEvent(NutrientsFormEvent.OnIngredientNameChange(it)) },
                label = stringResource(R.string.ingredient_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterName),
                maxLength = 20,
                onShowError = {},
                onNext = {
                    focusRequesterFats.requestFocus()
                })
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Per 100 gram",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)

                ) {
                    CustomOutlinedTextField(
                        value = uiState.fats,
                        onValueChange = { viewModel.onEvent(NutrientsFormEvent.OnFatsChange(it)) },
                        label = stringResource(R.string.fats),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterFats),
                        isNumeric = true,
                        maxLength = 5,
                        onShowError = {},
                        onNext = { focusRequesterCarbs.requestFocus() })
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomOutlinedTextField(
                        value = uiState.carbs,
                        onValueChange = { viewModel.onEvent(NutrientsFormEvent.OnCarbsChange(it)) },
                        label = stringResource(R.string.carbs),
                        modifier = Modifier.focusRequester(focusRequesterCarbs),
                        isNumeric = true,
                        maxLength = 5,
                        onShowError = {},
                        onNext = {
                            focusRequesterProteins.requestFocus()
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomOutlinedTextField(
                        value = uiState.proteins,
                        onValueChange = {
                            viewModel.onEvent(NutrientsFormEvent.OnProteinsChange(it))
                        },
                        label = stringResource(R.string.proteins),
                        modifier = Modifier.focusRequester(focusRequesterProteins),
                        isNumeric = true,
                        maxLength = 5,
                        onShowError = {

                        },
                        onNext = {
                            focusRequesterKkal.requestFocus()
                        })
                }

                CalculateWarning(
                    modifier = Modifier.weight(1f),
                    fats = uiState.fats.toDoubleOrNull() ?: 0.0,
                    proteins = uiState.proteins.toDoubleOrNull() ?: 0.0,
                    carbs = uiState.carbs.toDoubleOrNull() ?: 0.0,
                    setCalculateCalories = { countedCalories ->
                        viewModel.onEvent(NutrientsFormEvent.OnKkalChange(countedCalories.toString()))
                    })

            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomOutlinedTextField(
                value = uiState.kkal,
                onValueChange = {
                    viewModel.onEvent(NutrientsFormEvent.OnKkalChange(it))
                },
                label = stringResource(R.string.kkal),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterKkal),
                isNumeric = true,
                maxLength = 5,
                onShowError = {},
                onNext = {
                    focusRequesterWeight.requestFocus()
                })
            Spacer(modifier = Modifier.height(8.dp))
            CustomOutlinedTextField(
                value = uiState.weight,
                onValueChange = {
                    viewModel.onEvent(NutrientsFormEvent.OnWeightChange(it))
                },
                label = stringResource(R.string.weight),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterWeight),
                isNumeric = true,
                maxLength = 5,
                onShowError = {},
                onNext = { keyboardController?.hide() }
            )
        }

        val imeBottomPadding = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
        val safeBottomPadding = (imeBottomPadding - bottomBarHeight).coerceAtLeast(0.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = safeBottomPadding
                )
        ) {
            if (isEditing) {
                DualButtonPanel(
                    leftButtonIcon = Icons.Default.Clear,
                    leftButtonText = R.string.cancel,
                    rightButtonIcon = Icons.Default.Check,
                    rightButtonText = R.string.save,
                    enabledRightButton = uiState.isFormValid,
                    onClickButton = { button ->
                        when (button) {
                            LEFT_BUTTON -> viewModel.onEvent(NutrientsFormEvent.OnBackClick)
                            RIGHT_BUTTON -> viewModel.onEvent(NutrientsFormEvent.OnAddIngredientClick)
                        }
                    })
            } else {
                DualButtonPanel(
                    leftButtonIcon = Icons.Default.Add,
                    leftButtonText = R.string.add_ingredient,
                    rightButtonIcon = Icons.Default.Check,
                    rightButtonText = R.string.next_step,
                    enabledLeftButton = uiState.isFormValid,
                    enabledRightButton = isFirstIngredient,
                    onClickButton = { button ->
                        when (button) {
                            LEFT_BUTTON -> viewModel.onEvent(NutrientsFormEvent.OnAddIngredientClick)
                            RIGHT_BUTTON -> viewModel.onEvent(NutrientsFormEvent.OnNextStepClick)
                        }
                    })
            }
        }
    }
}


@Composable
fun CalculateWarning(
    modifier: Modifier = Modifier,
    fats: Double = 0.0,
    proteins: Double = 0.0,
    carbs: Double = 0.0,
    setCalculateCalories: (Double) -> Unit
) {
    val calories = (fats * 9.0) + (proteins * 4.0) + (carbs * 4.0)
    val roundedCalories = MathUtil.roundToDouble(calories)

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.End)
                    .size(16.dp)
                    .clickable {

                    }, imageVector = Icons.Default.Info, contentDescription = ""
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.text_counted_calorie),
                softWrap = true,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            NumericRoller(number = roundedCalories, isNeedToStartZero = true)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    setCalculateCalories.invoke(roundedCalories)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(
                    text = stringResource(R.string.use_calories),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutrientsFormPreview() {
    //NutrientsForm(onAddIngredient = {}, onFormSubmit = {}, onNextStep = {})
}