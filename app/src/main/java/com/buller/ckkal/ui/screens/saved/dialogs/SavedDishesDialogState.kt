package com.buller.ckkal.ui.screens.saved.dialogs

import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.home.dialogs.HomeDialogState

sealed class SavedDishesDialogState {
    data object Closed : SavedDishesDialogState()
    data object AddIngredient : SavedDishesDialogState()
    data object AllWeightDish : SavedDishesDialogState()
    data class DeleteIngredient(val ingredient: Ingredient) : SavedDishesDialogState()
    data class EditIngredient(val ingredient: Ingredient) : SavedDishesDialogState()
    data class EditWeight(val weight: Double) : SavedDishesDialogState()

}