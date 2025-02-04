package com.buller.ckkal.domain.usecases

import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.domain.interfaces.LocalDatabaseRepository

class DeleteDishUseCase(private val repository: LocalDatabaseRepository) {

    suspend fun execute(dish: Dish) {
        repository.deleteDish(dish)
    }

}