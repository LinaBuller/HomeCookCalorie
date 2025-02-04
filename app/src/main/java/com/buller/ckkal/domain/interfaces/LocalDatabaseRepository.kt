package com.buller.ckkal.domain.interfaces

import com.buller.ckkal.data.dto.DishWithIngredients
import kotlinx.coroutines.flow.Flow
import com.buller.ckkal.domain.utils.Result
import com.buller.ckkal.domain.entities.Dish

interface LocalDatabaseRepository {
    fun getDishesWithIngredients(): Flow<Result<List<DishWithIngredients>>>
    suspend fun setOrUpdateDish(dish: Dish)
    suspend fun deleteDish(dish: Dish)
}