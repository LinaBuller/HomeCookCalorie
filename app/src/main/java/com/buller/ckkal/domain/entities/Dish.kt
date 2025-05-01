package com.buller.ckkal.domain.entities

import com.buller.ckkal.data.dto.DishDto
import kotlinx.serialization.Serializable

@Serializable
data class Dish(
     val id: String = "",
     val name: String = "",
    var allKcal: Double,
    var allFats: Double,
    var allCarbs: Double,
    var allProteins: Double,
    var allWeight: Double,
    var ingredients: List<Ingredient> = listOf()
)  {
    fun toDishDto(): DishDto {
        return DishDto().apply {
            id = this@Dish.id
            name = this@Dish.name
            allKcal = this@Dish.allKcal
            allFats = this@Dish.allFats
            allCarbs = this@Dish.allCarbs
            allProteins = this@Dish.allProteins
            allWeight = this@Dish.allWeight
        }
    }
}