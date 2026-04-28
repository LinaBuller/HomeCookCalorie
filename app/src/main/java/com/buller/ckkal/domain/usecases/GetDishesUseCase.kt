package com.buller.ckkal.domain.usecases

import com.buller.ckkal.data.dto.DishWithIngredients
import com.buller.ckkal.domain.utils.Result
import com.buller.ckkal.domain.interfaces.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow


class GetDishesUseCase(private val repository: LocalDatabaseRepository) {
    fun execute(): Flow<Result<List<DishWithIngredients>>> {
        return repository.getDishesWithIngredients()
    }
}