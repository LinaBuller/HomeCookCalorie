package com.buller.ckkal.ui.screens.saved

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.DeleteDialog
import com.buller.ckkal.ui.screens.home.dialogs.EditIngredientDialog
import com.buller.ckkal.ui.screens.saved.dialogs.SavedDishesDialogState

@Composable
fun SavedDishesDialogManager(
    dialogState: SavedDishesDialogState,
    onDeleteIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
    onDismissRequest: () -> Unit,
    onWeightSet: (Double) -> Unit
) {
    when (dialogState) {
        is SavedDishesDialogState.EditIngredient -> {
            EditIngredientDialog(ingredient = dialogState.ingredient, onSave = {
                onEditIngredient(it)
                onDismissRequest()
            }, onDismiss = {
                onDismissRequest()
            })
        }

        is SavedDishesDialogState.DeleteIngredient -> {
            DeleteDialog(item = dialogState.ingredient,
                confirmationText = stringResource(R.string.delete),
                title = stringResource(R.string.delete_ingredient),
                onConfirm = {
                    onDeleteIngredient(it)
                    onDismissRequest()
                },
                onDismiss = {
                    onDismissRequest()
                })
        }
        is SavedDishesDialogState.Closed -> {}
    }
}