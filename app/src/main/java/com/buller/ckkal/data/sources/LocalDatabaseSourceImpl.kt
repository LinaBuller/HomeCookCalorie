package com.buller.ckkal.data.sources

import com.buller.ckkal.data.dto.DishDto
import com.buller.ckkal.data.dto.DishWithIngredients
import com.buller.ckkal.domain.interfaces.LocalDatabaseSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.buller.ckkal.data.room.DishDao

class LocalDatabaseSourceImpl @Inject constructor(private val dao: DishDao) : LocalDatabaseSource {

    override fun getDishesWithIngredients(): Flow<List<DishWithIngredients>> =
        dao.getAllDishesWithIngredients()

    override suspend fun setOrUpdateDishWithIngredients(dishWithIngredients: DishWithIngredients) {
        dao.insertDish(dishWithIngredients.dish)
        dishWithIngredients.ingredients.forEach {
            dao.insertIngredient(it)
        }
    }

    override suspend fun deleteDish(dish: DishDto) = dao.deleteDish(dish)
    override suspend fun updateDish(dish: DishDto) = dao.updateDish(dish)
}