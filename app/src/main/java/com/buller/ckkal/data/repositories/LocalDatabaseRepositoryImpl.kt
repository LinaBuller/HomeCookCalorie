package com.buller.ckkal.data.repositories

import com.buller.ckkal.data.dto.DishWithIngredients
import com.buller.ckkal.domain.interfaces.LocalDatabaseRepository
import com.buller.ckkal.domain.interfaces.LocalDatabaseSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.buller.ckkal.domain.utils.Result
import com.buller.ckkal.domain.entities.Dish
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.transform

class LocalDatabaseRepositoryImpl @Inject constructor(private val source: LocalDatabaseSource) :
    LocalDatabaseRepository {

    override fun getDishesWithIngredients(): Flow<Result<List<DishWithIngredients>>> =
        source.getDishesWithIngredients()
            .transform { dishesWithIngredients ->
                emit(Result.Loading)
                emit(Result.Success(dishesWithIngredients))
            }
            .catch { emit(Result.Error(Exception(it))) }

    override suspend fun setOrUpdateDish(dish: Dish) {
        val dishDto = dish.toDishDto()
        val ingredients = dish.ingredients.map { ingredient ->
            ingredient.toIngredientDto().apply {
                dishId = dishDto.id
            }
        }
        source.setOrUpdateDishWithIngredients(
            DishWithIngredients(
                dish = dishDto,
                ingredients = ingredients
            )
        )
    }

    override suspend fun deleteDish(dish: Dish) {
        val dishDto = dish.toDishDto()
        source.deleteDish(dishDto)
    }
}