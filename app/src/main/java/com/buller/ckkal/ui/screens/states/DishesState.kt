package com.buller.ckkal.ui.screens.states

import com.buller.ckkal.domain.entities.Dish

data class DishesState(
    val dishes: List<Dish>,
    val isLoading: Boolean = false,
    val errorMessages: Throwable? = null
)