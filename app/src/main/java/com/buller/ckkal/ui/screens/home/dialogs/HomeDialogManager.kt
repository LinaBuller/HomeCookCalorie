package com.buller.ckkal.ui.screens.home.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.DeleteDialog
import com.buller.ckkal.ui.screens.states.DishState

@Composable
fun HomeDialogManager(
    dialogState: HomeDialogState,
    dishState: DishState,
    setIngredient: (Ingredient) -> Unit,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
    onSaveDish: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onAllWeightDishComplete: (Double) -> Unit
) {
    when (dialogState) {
        is HomeDialogState.AddIngredient -> {
            AddIngredientDialog(onDismiss = { onDismissRequest() },
                onAddIngredient = { ingredient ->
                    setIngredient(ingredient)
                    onDismissRequest()
                })
        }

        is HomeDialogState.AllWeightDish -> {
            AllWeightDishDialog(
                onDismiss = { onDismissRequest() },
                onAddAllWeight = { weight ->
                    onAllWeightDishComplete(weight)
                })
        }

        is HomeDialogState.CalculateCurrentDish -> {
            CalculationDialog(
                onDismiss = { onDismissRequest() },
                state = dishState,
                onSaveDishes = {dishName->
                    onSaveDish(dishName)
                    onDismissRequest()
                })
        }

        is HomeDialogState.DeleteIngredient -> {
            DeleteDialog(item = dialogState.ingredient,
                confirmationText = stringResource(R.string.delete),
                title = stringResource(R.string.delete_ingredient),
                onConfirm = {
                    onRemoveIngredient(it)
                    onDismissRequest()
                },
                onDismiss = {
                    onDismissRequest()
                })
        }

        is HomeDialogState.EditIngredient -> {
            EditIngredientDialog(ingredient = dialogState.ingredient, onSave = {
                onEditIngredient(it)
                onDismissRequest()
            }, onDismiss = {
                onDismissRequest()
            })
        }

        is HomeDialogState.Closed -> {}
    }
}