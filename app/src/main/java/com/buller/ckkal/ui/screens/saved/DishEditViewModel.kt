package com.buller.ckkal.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buller.ckkal.domain.entities.CalculatedNutrient
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.domain.usecases.UpdateDishUseCase
import com.buller.ckkal.domain.utils.MathUtil
import com.buller.ckkal.ui.screens.states.DishState
import com.buller.ckkal.ui.screens.states.IngredientsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class DishEditViewModel
@Inject constructor(
    private val updateDishUseCase: UpdateDishUseCase,
) : ViewModel() {
    private val _ingredientsState = MutableStateFlow(IngredientsState(ingredients = emptyList()))
    val ingredientsState: StateFlow<IngredientsState> = _ingredientsState
    private val _dish = MutableStateFlow(DishState())
    val dish: StateFlow<DishState> = _dish

    fun initDish(dish: Dish) {
        _dish.update {
            it.copy(
                id = dish.itemId,
                name = dish.name,
                finalFats = dish.allFats,
                finalProteins = dish.allProteins,
                finalCarbs = dish.allCarbs,
                finalKcal = dish.allKcal,
                totalWeight = dish.allWeight
            )
        }
        _ingredientsState.update {
            it.copy(ingredients = dish.ingredients)
        }
    }

    fun updateOldDish() {
        val dish = Dish(
            itemId = _dish.value.id,
            ingredients = _ingredientsState.value.ingredients,
            name = _dish.value.name,
            allWeight = _dish.value.totalWeight,
            allFats = _dish.value.finalFats,
            allKcal = _dish.value.finalKcal,
            allCarbs = _dish.value.finalCarbs,
            allProteins = _dish.value.finalProteins
        )
        updateDish(dish)
    }

    fun calculateCurrentDish() {
        if (_dish.value.totalWeight <= 0) return
        val nutrient = calculateNutrient()
        val newDish = _dish.value.copy(
            finalFats = nutrient.finalFats,
            finalProteins = nutrient.finalProteins,
            finalKcal = nutrient.finalKcal,
            finalCarbs = nutrient.finalCarbs
        )
        _dish.update {
            newDish
        }
    }

    private fun calculateNutrient(): CalculatedNutrient {
        return MathUtil.calculateNutrient(
            dishWeight = _dish.value.totalWeight,
            ingredientsState = _ingredientsState.value,
        )
    }

    fun setWeight(weight: Double) {
        _dish.update {
            it.copy(totalWeight = weight)
        }
    }

    private fun updateDish(dish: Dish) {
        viewModelScope.launch {
            updateDishUseCase.execute(dish)
        }
    }

    fun setIngredient(ingredient: Ingredient) {
        _ingredientsState.update {
            it.copy(ingredients = it.ingredients + ingredient)
        }
    }

    fun removeIngredient(ingredient: Ingredient) {
        _ingredientsState.update {
            it.copy(ingredients = it.ingredients - ingredient)
        }
    }

    fun editIngredient(ingredient: Ingredient) {
        _ingredientsState.update { currentState ->
            val updatedIngredients = currentState.ingredients.map { currentIngredient ->
                if (currentIngredient.ingredientId == ingredient.ingredientId) {
                    ingredient
                } else {
                    currentIngredient
                }
            }
            currentState.copy(ingredients = updatedIngredients)
        }
    }
}
