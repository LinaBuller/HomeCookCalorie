package com.buller.ckkal.ui.screens.states

data class NutrientEditState(
    val name: String = "",
    val fats: String = "",
    val kcal: String = "",
    val carbs: String = "",
    val proteins: String = "",
    val weight: String = ""
)

typealias OnNutrientChange = (String, String) -> Unit
