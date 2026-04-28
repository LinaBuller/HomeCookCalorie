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
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
    onDismissRequest: () -> Unit,
) {
    when (dialogState) {
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