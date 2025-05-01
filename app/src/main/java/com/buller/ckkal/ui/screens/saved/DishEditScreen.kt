package com.buller.ckkal.ui.screens.saved

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.DualButtonPanel
import com.buller.ckkal.ui.screens.LEFT_BUTTON
import com.buller.ckkal.ui.screens.RIGHT_BUTTON
import com.buller.ckkal.ui.screens.home.IngredientList
import com.buller.ckkal.ui.screens.roller.NumericRoller
import com.buller.ckkal.ui.screens.saved.dialogs.SavedDishesDialogState
import com.buller.ckkal.ui.screens.states.DishState
import com.buller.ckkal.ui.screens.states.IngredientsState


@Composable
fun DishEditScreenRoute(
    modifier: Modifier = Modifier,
    dish: Dish,
    dishEditViewModel: DishEditViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    SideEffect {
        dishEditViewModel.initDish(dish)
    }

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
        onAddIngredient = dishEditViewModel::setIngredient,
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
    onAddIngredient: (Ingredient) -> Unit,
    onBack: () -> Unit,
) {
    val ingredientsState = dishEditViewModel.ingredientsState.collectAsState()
    val dishState = dishEditViewModel.dish.collectAsState()
    var dialogState by remember { mutableStateOf<SavedDishesDialogState>(SavedDishesDialogState.Closed) }

    EditDishView(
        modifier = modifier,
        dishState = dishState.value,
        ingredientsState = ingredientsState.value,
        onBack = onBack,
        dialogState = { dialogState = it },
        onSaveDish = onSaveDish
    )

    SavedDishesDialogManager(
        dialogState = dialogState,
        onAddIngredient = onAddIngredient,
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
    onBack: () -> Unit,
    dialogState: (SavedDishesDialogState) -> Unit,
    onSaveDish: () -> Unit
) {

    Scaffold(
        topBar = {
            EditTopBar(
                onBack = onBack,
                onAddIngredient = {
                    dialogState(SavedDishesDialogState.AddIngredient)
                })
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DishPart(
                state = dishState,
                showChangeWeight = { weight ->
                    dialogState(SavedDishesDialogState.EditWeight(weight))
                })


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
                })

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
}

@Composable
fun DishPart(
    state: DishState,
    showChangeWeight: (Double) -> Unit,
) {
    var weight by remember { mutableStateOf(state.totalWeight.toString()) }

    LaunchedEffect(state.totalWeight) {
        weight = state.totalWeight.toString()
    }

    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                text = state.name, style = MaterialTheme.typography.titleLarge, fontSize = 26.sp
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                LabeledRollerValue(value = state.finalKcal, label = R.string.kkal)
                LabeledRollerValue(value = state.finalFats, label = R.string.fats)
                LabeledRollerValue(value = state.finalCarbs, label = R.string.carbs)
                LabeledRollerValue(value = state.finalProteins, label = R.string.proteins)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            WeightValue(weight = weight, showChangeWeight = showChangeWeight)
        }
    }
}

@Composable
fun WeightValue(modifier: Modifier = Modifier, weight: String, showChangeWeight: (Double) -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    Column(horizontalAlignment = Alignment.Start) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.clickable {
                    clipboardManager.setText(AnnotatedString(weight))
                },
                text = weight,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 30.sp
            )
            IconButton(onClick = {
                showChangeWeight(weight.toDouble())
            }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.weight)
                )
            }
        }
        Text(
            text = stringResource(R.string.weight),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTopBar(
    onBack: () -> Unit, onAddIngredient: () -> Unit
) {

    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(R.string.edit_dish),
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }, navigationIcon = {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.cancel)
            )
        }
    }, actions = {
        FilledIconButton(onClick = onAddIngredient) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.cancel)
            )
        }
    })
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
        dialogState = {})
}