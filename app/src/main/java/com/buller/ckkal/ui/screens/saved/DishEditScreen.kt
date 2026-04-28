package com.buller.ckkal.ui.screens.saved

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.views.DualButtonPanel
import com.buller.ckkal.ui.screens.views.LEFT_BUTTON
import com.buller.ckkal.ui.screens.views.RIGHT_BUTTON
import com.buller.ckkal.ui.screens.home.IngredientList
import com.buller.ckkal.ui.screens.roller.NumericRoller
import com.buller.ckkal.ui.screens.saved.dialogs.SavedDishesDialogState
import com.buller.ckkal.ui.screens.states.DishState
import com.buller.ckkal.ui.screens.states.IngredientsState


@Composable
fun DishEditScreenRoute(
    modifier: Modifier = Modifier,
    dishEditViewModel: DishEditViewModel,
    navigateToAddTotalWeightScreen: (Double) -> Unit,
    onBack: () -> Unit
) {
    DishEditScreen(
        modifier = modifier,
        dishEditViewModel = dishEditViewModel,
        onWeightSet = dishEditViewModel::setWeight,
        onSaveDish = {
            dishEditViewModel.updateOldDish()
            onBack()
        },
        onDeleteIngredient = dishEditViewModel::deleteIngredient,
        onEditIngredient = dishEditViewModel::editIngredient,
        navigateToAddTotalWeightScreen = {
            navigateToAddTotalWeightScreen.invoke(dishEditViewModel.dish.value.totalWeight)
        },
        onBack = onBack
    )
}

@Composable
fun DishEditScreen(
    modifier: Modifier = Modifier,
    dishEditViewModel: DishEditViewModel,
    onWeightSet: (Double) -> Unit,
    onSaveDish: () -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
    navigateToAddTotalWeightScreen: () -> Unit,
    onBack: () -> Unit,
) {
    val ingredientsState = dishEditViewModel.ingredientsState.collectAsStateWithLifecycle()
    val dishState = dishEditViewModel.dish.collectAsStateWithLifecycle()

    var dialogState by remember { mutableStateOf<SavedDishesDialogState>(SavedDishesDialogState.Closed) }

    EditDishView(
        modifier = modifier,
        dishState = dishState.value,
        ingredientsState = ingredientsState.value,
        onBack = onBack,
        dialogState = { dialogState = it },
        onSaveDish = onSaveDish,
        navigateToAddTotalWeightScreen = navigateToAddTotalWeightScreen
    )

    SavedDishesDialogManager(
        dialogState = dialogState,
        onEditIngredient = onEditIngredient,
        onWeightSet = onWeightSet,
        onDismissRequest = { dialogState = SavedDishesDialogState.Closed },
        onDeleteIngredient = onDeleteIngredient
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDishView(
    modifier: Modifier = Modifier,
    dishState: DishState,
    ingredientsState: IngredientsState,
    dialogState: (SavedDishesDialogState) -> Unit,
    onSaveDish: () -> Unit,
    navigateToAddTotalWeightScreen: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        DishPart(
            state = dishState,
            onChangeWeight = navigateToAddTotalWeightScreen
        )

        IngredientList(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            listOfIngredients = ingredientsState.ingredients,
            onDeleteIngredient = { ingredient ->
                dialogState(SavedDishesDialogState.DeleteIngredient(ingredient))
            },
            onEditIngredient = { ingredient ->
                dialogState(SavedDishesDialogState.EditIngredient(ingredient))
            }
        )

        DualButtonPanel(
            leftButtonIcon = Icons.Default.Close,
            leftButtonText = R.string.cancel,
            rightButtonIcon = Icons.Default.Done,
            rightButtonText = R.string.save
        ) { buttonType ->
            when (buttonType) {
                LEFT_BUTTON -> onBack()
                RIGHT_BUTTON -> onSaveDish()
            }
        }
    }
}

@Composable
fun DishPart(
    state: DishState,
    onChangeWeight: () -> Unit,
) {
    var weight by remember { mutableStateOf(state.totalWeight) }

    val scrollableState = rememberScrollState()

//    LaunchedEffect(state.totalWeight) {
//        weight = state.totalWeight.toString()
//    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 16.dp)
            .verticalScroll(scrollableState)
    ) {
        Text(
            text = state.name,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                LabeledRollerValue(value = state.finalFats, label = R.string.fats)
                Spacer(modifier = Modifier.height(8.dp))
                LabeledRollerValue(value = state.finalKcal, label = R.string.kkal)
            }
            Column {
                LabeledRollerValue(value = state.finalCarbs, label = R.string.carbs)
                Spacer(modifier = Modifier.height(8.dp))
                Row (verticalAlignment = Alignment.Bottom){
                    LabeledRollerValue(value = state.totalWeight, label = R.string.weight)
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable { onChangeWeight() },
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.weight)
                    )
                }
            }
            LabeledRollerValue(value = state.finalProteins, label = R.string.proteins)
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun LabeledRollerValue(
    value: Double,
    @StringRes label: Int
) {
    val clipboardManager = LocalClipboardManager.current
    Column(horizontalAlignment = Alignment.Start) {
        NumericRoller(number = value, modifier = Modifier.clickable {
            clipboardManager.setText(AnnotatedString("$value"))
        })
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            modifier = Modifier.clickable {
                clipboardManager.setText(AnnotatedString("$value"))
            },
            text = stringResource(label),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DishEditScreenPreview() {
    val ingredients = listOf(
        Ingredient(
            id = "1",
            name = "Ingredient 1",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4,
            dishId = "1"
        ), Ingredient(
            id = "2",
            name = "Ingredient 2",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4,
            dishId = "2"
        ), Ingredient(
            id = "3",
            name = "Ingredient 3",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4,
            dishId = "3"
        )
    )
    val ingredientsState = IngredientsState(ingredients = ingredients)
    val dishState = DishState(
        name = "Блинчик",
        totalWeight = 230.0,
        finalFats = 0.0,
        finalKcal = 0.0,
        finalProteins = 0.0,
        finalCarbs = 0.0
    )

    EditDishView(
        ingredientsState = ingredientsState,
        dishState = dishState,
        onBack = {},
        onSaveDish = {},
        dialogState = {},
        navigateToAddTotalWeightScreen = {})
}

