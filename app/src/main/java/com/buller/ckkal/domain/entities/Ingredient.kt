package com.buller.ckkal.domain.entities

import com.buller.ckkal.data.dto.IngredientDto
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    var id: String,
    var dishId: String,
    var name: String?,
    var kcal: Double,
    var fats: Double,
    var carbs: Double,
    var proteins: Double,
    var weight: Double
){
    fun toIngredientDto(): IngredientDto {
        return IngredientDto(
            ingredientId = id,
            name = name,
            kcal = kcal,
            fats = fats,
            carbs = carbs,
            proteins = proteins,
            weight = weight,
            dishId = dishId
        )
    }
}