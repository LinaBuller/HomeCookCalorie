package com.buller.ckkal.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buller.ckkal.domain.utils.Result
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.domain.usecases.DeleteDishUseCase
import com.buller.ckkal.domain.usecases.GetDishesUseCase
import com.buller.ckkal.domain.usecases.SetDishUseCase
import com.buller.ckkal.domain.usecases.UpdateDishUseCase
import com.buller.ckkal.domain.utils.MathUtil
import com.buller.ckkal.ui.screens.states.DishState
import com.buller.ckkal.ui.screens.states.DishesState
import com.buller.ckkal.ui.screens.states.IngredientsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val deleteDishUseCase: DeleteDishUseCase,
    private val getDishesUseCase: GetDishesUseCase,
    private val setDishUseCase: SetDishUseCase,
    private val updateDishUseCase: UpdateDishUseCase
) : ViewModel() {
    private val _ingredientsState = MutableStateFlow(IngredientsState(ingredients = emptyList()))
    val ingredientsState: StateFlow<IngredientsState> = _ingredientsState
    private val _dish = MutableStateFlow(DishState())
    val dish: StateFlow<DishState> = _dish
    private val _savedDishesState = MutableStateFlow(DishesState(dishes = emptyList()))
    val savedDishesState: StateFlow<DishesState> = _savedDishesState


    init {
        getDishes()
    }

    fun setIngredient(ingredient: Ingredient) {
        _ingredientsState.value =
            _ingredientsState.value.copy(ingredients = _ingredientsState.value.ingredients + ingredient)
    }

    fun setWeight(weight: Double) {
        _dish.update {
            it.copy(totalWeight = weight)
        }
    }

    fun calculateCurrentDish() {
        val nutrients = MathUtil.calculateNutrient(
            dishWeight = _dish.value.totalWeight,
            ingredientsState = _ingredientsState.value
        )
        _dish.update {
            it.copy(
                finalKcal = nutrients.finalKcal,
                finalCarbs = nutrients.finalCarbs,
                finalFats = nutrients.finalFats,
                finalProteins = nutrients.finalProteins
            )
        }
    }

    private fun getDishes() {
        _savedDishesState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getDishesUseCase.execute()
            result.collect { res ->
                _savedDishesState.update { currentState ->
                    when (res) {
                        is Result.Success -> {
                            currentState.copy(
                                dishes = res.data.map { dishWithIngredients -> dishWithIngredients.toDish() },
                                isLoading = false
                            )
                        }

                        is Result.Error -> {
                            currentState.copy(
                                dishes = emptyList(),
                                isLoading = false,
                                errorMessages = res.exception
                            )
                        }

                        is Result.Loading -> {
                            currentState.copy(dishes = emptyList(), isLoading = true)
                        }

                    }
                }
            }
        }
    }

    private fun setDish(dish: Dish) {
        viewModelScope.launch {
            setDishUseCase.execute(dish)
        }
    }

    fun updateDish(dish: Dish) {
        viewModelScope.launch {
            updateDishUseCase.execute(dish)
        }
    }

    fun deleteDish(dish: Dish) {
        viewModelScope.launch {
            try {
                deleteDishUseCase.execute(dish)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error deleting dish:$e")
            }
        }
    }

    fun saveDish() {
        val dish = Dish(
            id = UUID.randomUUID().toString(),
            ingredients = _ingredientsState.value.ingredients,
            allKcal = _dish.value.finalKcal,
            allFats = _dish.value.finalFats,
            allCarbs = _dish.value.finalCarbs,
            allProteins = _dish.value.finalProteins,
            allWeight = _dish.value.totalWeight,
            name = _dish.value.name.ifBlank { "Some dish" }
        )
        setDish(dish)
        cleanDishWithIngredientsList()
    }

    private fun cleanDishWithIngredientsList() {
        _dish.value = _dish.value.copy(
            finalCarbs = 0.0,
            finalFats = 0.0,
            finalKcal = 0.0,
            finalProteins = 0.0,
            totalWeight = 0.0
        )
        _ingredientsState.value = _ingredientsState.value.copy(ingredients = emptyList())
    }

    fun removeIngredient(ingredient: Ingredient) {
        _ingredientsState.update { currentState ->
            currentState.copy(ingredients = _ingredientsState.value.ingredients - ingredient)
        }
    }

    fun editIngredient(ingredient: Ingredient) {
        _ingredientsState.update { currentState ->
            val updatedIngredients = currentState.ingredients.map { currentIngredient ->
                if (currentIngredient.id == ingredient.id) {
                    ingredient
                } else {
                    currentIngredient
                }
            }
            currentState.copy(ingredients = updatedIngredients)
        }
    }

    fun setDishName(name: String) {
        if (name.isNullOrBlank()) return
        _dish.update { it.copy(name = name) }
    }
}