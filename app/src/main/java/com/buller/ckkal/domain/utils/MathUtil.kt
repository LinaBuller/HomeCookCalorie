package com.buller.ckkal.domain.utils

import com.buller.ckkal.domain.entities.CalculatedNutrient
import com.buller.ckkal.ui.screens.states.IngredientsState
import java.math.BigDecimal
import java.math.RoundingMode

object MathUtil {

    fun roundToDouble(value: Double): Double {
        return BigDecimal(value).setScale(1, RoundingMode.HALF_UP)
            .toDouble()
    }

    fun calculateNutrient(
        dishWeight: Double,
        ingredientsState: IngredientsState
    ): CalculatedNutrient {
        val ingredients = ingredientsState.ingredients
        var sumKcal = 0.0
        var sumProtein = 0.0
        var sumCarbs = 0.0
        var sumFats = 0.0

        ingredients.forEach { ingredient ->
            val weight = ingredient.weight
            sumKcal += calculate(weight, ingredient.kcal)
            sumCarbs += calculate(weight, ingredient.carbs)
            sumFats += calculate(weight, ingredient.fats)
            sumProtein += calculate(weight, ingredient.proteins)
        }

        val totalWeight = roundToDouble(dishWeight)
        val finalKcal = roundToDouble((sumKcal / totalWeight) * 100)
        val finalProtein = roundToDouble((sumProtein / totalWeight) * 100)
        val finalFats = roundToDouble((sumFats / totalWeight) * 100)
        val finalCarbs = roundToDouble((sumCarbs / totalWeight) * 100)
        return CalculatedNutrient(
            finalFats = finalFats,
            finalProteins = finalProtein,
            finalCarbs = finalCarbs,
            finalKcal = finalKcal
        )
    }

    private fun calculate(weightIngredient: Double, weightNutrient: Double): Double {
        return (weightIngredient * weightNutrient) / 100
    }
}