package com.buller.ckkal.domain.interfaces

import com.buller.ckkal.data.dto.DishDto
import com.buller.ckkal.data.dto.DishWithIngredients
import kotlinx.coroutines.flow.Flow


interface LocalDatabaseSource {
    fun getDishesWithIngredients(): Flow<List<DishWithIngredients>>
    suspend fun setOrUpdateDishWithIngredients(dish: DishWithIngredients)
    suspend fun deleteDish(dish: DishDto)
    suspend fun updateDish(dish: DishDto)
}