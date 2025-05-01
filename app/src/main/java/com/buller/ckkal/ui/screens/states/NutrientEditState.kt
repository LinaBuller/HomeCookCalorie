package com.buller.ckkal.ui.screens.states

data class NutrientEditState(
    val name: String = "",
    val fats: String = "",
    val kcal: String = "",
    val carbs: String = "",
    val proteins: String = "",
    val weight: String = ""
){
    companion object {
        const val NAME = "name"
        const val FATS = "fats"
        const val KCAL = "kcal"
        const val CARBS = "carbs"
        const val PROTEINS = "proteins"
        const val WEIGHT = "weight"
    }
}
typealias OnNutrientChange = (String, String) -> Unit