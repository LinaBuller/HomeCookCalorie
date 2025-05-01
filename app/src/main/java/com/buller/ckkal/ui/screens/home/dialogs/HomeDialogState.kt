package com.buller.ckkal.ui.screens.home.dialogs

import com.buller.ckkal.domain.entities.Ingredient

sealed class HomeDialogState {
    data object Closed : HomeDialogState()
    data object AddIngredient : HomeDialogState()
    data object AllWeightDish : HomeDialogState()
    data object CalculateCurrentDish : HomeDialogState()
    data class DeleteIngredient(val ingredient: Ingredient) : HomeDialogState()
    data class EditIngredient(val ingredient: Ingredient) : HomeDialogState()
}