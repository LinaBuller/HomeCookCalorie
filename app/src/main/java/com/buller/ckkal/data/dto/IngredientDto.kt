package com.buller.ckkal.data.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.buller.ckkal.domain.entities.Ingredient

@Entity(
    tableName = "ingredients",
    foreignKeys = [ForeignKey(
        entity = DishDto::class,
        parentColumns = ["id"],
        childColumns = ["dishId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class IngredientDto(
    @PrimaryKey()
    var ingredientId: String = "",
    var name: String? = "",
    var kcal: Double = 0.0,
    var fats: Double = 0.0,
    var carbs: Double = 0.0,
    var proteins: Double = 0.0,
    var weight: Double = 0.0,
    var dishId: String = ""
) {
    fun toIngredient(): Ingredient {
        return Ingredient(
            ingredientId = ingredientId,
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