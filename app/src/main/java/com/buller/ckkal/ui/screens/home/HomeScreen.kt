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
import com.buller.ckkal.ui.screens.views.DualButtonPanel
import com.buller.ckkal.ui.screens.views.LEFT_BUTTON
import com.buller.ckkal.ui.screens.views.DataOrPlaceholder
import com.buller.ckkal.ui.screens.views.RIGHT_BUTTON
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.home.dialogs.HomeDialogManager
import com.buller.ckkal.ui.screens.home.views.CollapsibleCard
import com.buller.ckkal.ui.screens.home.dialogs.HomeDialogState
import com.buller.ckkal.ui.screens.states.DishState
import com.buller.ckkal.ui.screens.states.IngredientsState

@Composable
fun HomeRoute(
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navigateToAddIngredientScreen: () -> Unit,
    navigateToAddTotalWeight: () -> Unit
) {
    val state by sharedViewModel.ingredientsState.collectAsStateWithLifecycle()
    val dishState by sharedViewModel.dish.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        dishState = dishState,
        setWeight = sharedViewModel::setTotalWeight,
        onRemoveIngredient = sharedViewModel::removeIngredient,
        onEditIngredient = sharedViewModel::editIngredient,
        onAddIngredient = navigateToAddIngredientScreen,
        onAddTotalWeight = navigateToAddTotalWeight
    )
}

@Composable
fun HomeScreen(
    state: IngredientsState,
    dishState: DishState,
    setWeight: (Double) -> Unit,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
    onAddIngredient: () -> Unit,
    onAddTotalWeight: () -> Unit
) {
    val ingredients: List<Ingredient> = state.ingredients
    var dialogState by remember { mutableStateOf<HomeDialogState>(HomeDialogState.Closed) }

    Column(modifier = Modifier.imePadding().fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

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
        onRemoveIngredient = onRemoveIngredient,
        onEditIngredient = onEditIngredient,
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
        setWeight = {},
        onRemoveIngredient = {},
        onEditIngredient = {},
        onAddIngredient = {},
        onAddTotalWeight = {}
    )
}