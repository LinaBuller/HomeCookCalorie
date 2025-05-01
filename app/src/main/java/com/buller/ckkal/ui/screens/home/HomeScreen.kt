package com.buller.ckkal.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.ColorPairs
import com.buller.ckkal.ui.screens.DualButtonPanel
import com.buller.ckkal.ui.screens.LEFT_BUTTON
import com.buller.ckkal.ui.screens.DataOrPlaceholder
import com.buller.ckkal.ui.screens.RIGHT_BUTTON
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.home.dialogs.HomeDialogManager
import com.buller.ckkal.ui.screens.home.views.CollapsibleCard
import com.buller.ckkal.ui.screens.home.dialogs.HomeDialogState
import com.buller.ckkal.ui.screens.states.DishState
import com.buller.ckkal.ui.screens.states.IngredientsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    onAddIngredient: () -> Unit,
    onAddTotalWeight: () -> Unit
) {
    val state by sharedViewModel.ingredientsState.collectAsStateWithLifecycle()
    val dishState by sharedViewModel.dish.collectAsStateWithLifecycle()

    val calculateDish = {
        sharedViewModel.calculateCurrentDish()
    }
    val onSaveDish = { dishName: String ->
        sharedViewModel.setDishName(dishName)
        sharedViewModel.saveDish()
    }

    HomeScreen(
        modifier = modifier,
        state = state,
        dishState = dishState,
        setIngredient = sharedViewModel::setIngredient,
        setWeight = sharedViewModel::setWeight,
        onCalculateDish = calculateDish,
        onRemoveIngredient = sharedViewModel::removeIngredient,
        onEditIngredient = sharedViewModel::setIngredient,
        onSaveDish = onSaveDish,
        onAddIngredient = onAddIngredient,
        onAddTotalWeight = onAddTotalWeight
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: IngredientsState,
    dishState: DishState,
    setIngredient: (Ingredient) -> Unit,
    setWeight: (Double) -> Unit,
    onCalculateDish: () -> Unit,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
    onSaveDish: (String) -> Unit,
    onAddIngredient: () -> Unit,
    onAddTotalWeight: () -> Unit
) {
    val ingredients: List<Ingredient> = state.ingredients
    var dialogState by remember { mutableStateOf<HomeDialogState>(HomeDialogState.Closed) }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

        DataOrPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            isNotEmpty = ingredients.isNotEmpty(),
            title = R.string.no_ingredient,
            icon = R.drawable.food_healthy_icon,
        ) {
            IngredientList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                listOfIngredients = ingredients,
                onDeleteIngredient = { dialogState = HomeDialogState.DeleteIngredient(it) },
                onEditIngredient = { dialogState = HomeDialogState.EditIngredient(it) })
        }

        DualButtonPanel(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            enabledRightButton = ingredients.isNotEmpty(),
            leftButtonIcon = Icons.Default.Add,
            leftButtonText = R.string.add_ingredient,
            rightButtonIcon = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            rightButtonText = R.string.next_step,
        ) { buttonType ->
            when (buttonType) {
                LEFT_BUTTON -> {
                    onAddIngredient()
                }

                RIGHT_BUTTON -> {
                    onAddTotalWeight.invoke()
                }
            }
        }
    }

    HomeDialogManager(
        dialogState = dialogState,
        dishState = dishState,
        setIngredient = setIngredient,
        onRemoveIngredient = onRemoveIngredient,
        onEditIngredient = onEditIngredient,
        onSaveDish = onSaveDish,
        onAllWeightDishComplete = { weight ->
            setWeight(weight)
            CoroutineScope(Dispatchers.Main).launch {
                onCalculateDish.invoke()
                delay(300)
                dialogState = HomeDialogState.CalculateCurrentDish
            }
            //TODO Think about it
        },
        onDismissRequest = { dialogState = HomeDialogState.Closed }
    )
}

@Composable
fun IngredientList(
    modifier: Modifier = Modifier,
    listOfIngredients: List<Ingredient>,
    onDeleteIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
    ) {

        itemsIndexed(listOfIngredients, key = { _, item -> item.id }) { index, item ->
            val listColors = ColorPairs.getColors()
            CollapsibleCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (index * -8).dp),
                item = item,
                styleColor = if (index > listColors.size - 1) {
                    listColors[index - listColors.size]
                } else {
                    listColors[index]
                },
                onRemoveIngredient = onDeleteIngredient,
                onEditIngredient = onEditIngredient
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val ingredients = listOf(
        Ingredient(
            dishId = "1",
            id = "1",
            name = "Ingredient 1",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4
        ),
        Ingredient(
            dishId = "2",
            id = "2",
            name = "Ingredient 2",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4
        ), Ingredient(
            dishId = "3",
            id = "3",
            name = "Ingredient 3",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4
        )
    )
    val state = remember { IngredientsState(ingredients) }
    val dishState = remember { DishState() }
    HomeScreen(
        state = state,
        dishState = dishState,
        setIngredient = {},
        setWeight = {},
        onRemoveIngredient = {},
        onEditIngredient = {},
        onCalculateDish = {},
        onSaveDish = {},
        onAddIngredient = {},
        onAddTotalWeight = {}
    )
}