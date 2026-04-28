package com.buller.ckkal.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.buller.ckkal.domain.entities.Dish

data class DishWithIngredients(
    @Embedded val dish: DishDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "dishId"
    )
    val ingredients: List<IngredientDto>
) {
    fun toDish(): Dish {
        return Dish(
            id = dish.id,
            name = dish.name,
            allKcal = dish.allKcal,
            allFats = dish.allFats,
            allCarbs = dish.allCarbs,
            allProteins = dish.allProteins,
            allWeight = dish.allWeight,
            createdAt = dish.createdAt,
            ingredients = ingredients.map { it.toIngredient() }
        )
    }
}