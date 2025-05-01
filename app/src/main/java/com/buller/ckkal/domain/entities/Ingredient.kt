package com.buller.ckkal.domain.entities

import com.buller.ckkal.data.dto.IngredientDto
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Ingredient(
    var id: String = UUID.randomUUID().toString(),
    var dishId: String = "",
    var name: String? = null,
    var kcal: Double = 0.0,
    var fats: Double = 0.0,
    var carbs: Double = 0.0,
    var proteins: Double = 0.0,
    var weight: Double = 0.0
) {
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