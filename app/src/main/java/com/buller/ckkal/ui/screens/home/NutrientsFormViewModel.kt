package com.buller.ckkal.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buller.ckkal.domain.entities.Ingredient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class NutrientsFormState(
    val ingredientName: String = "",
    val kkal: String = "",
    val fats: String = "",
    val carbs: String = "",
    val proteins: String = "",
    val weight: String = "",
    val isFormValid: Boolean = false, // Состояние валидации
)

sealed class NutrientsFormEvent {
    data class OnIngredientNameChange(val name: String) : NutrientsFormEvent()
    data class OnKkalChange(val kkal: String) : NutrientsFormEvent()
    data class OnFatsChange(val fats: String) : NutrientsFormEvent()
    data class OnCarbsChange(val carbs: String) : NutrientsFormEvent()
    data class OnProteinsChange(val proteins: String) : NutrientsFormEvent()
    data class OnWeightChange(val weight: String) : NutrientsFormEvent()
    data object OnAddIngredientClick : NutrientsFormEvent()
    data object OnNextStepClick : NutrientsFormEvent()
    data object OnBackClick : NutrientsFormEvent()
}

sealed class NavigationEvent {
    data object NavigateToAddTotalWeight : NavigationEvent()
    data object NavigateToBack : NavigationEvent()
}

class NutrientsFormViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NutrientsFormState())
    val uiState: StateFlow<NutrientsFormState> = _uiState.asStateFlow()

    private val _ingredientAdded = MutableSharedFlow<Ingredient>()
    val ingredientAdded: SharedFlow<Ingredient> = _ingredientAdded.asSharedFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    fun onEvent(event: NutrientsFormEvent) {
        when (event) {
            is NutrientsFormEvent.OnIngredientNameChange -> {
                _uiState.value = _uiState.value.copy(ingredientName = event.name)
                validateForm()
            }

            is NutrientsFormEvent.OnKkalChange -> {
                _uiState.value = _uiState.value.copy(kkal = event.kkal)
                validateForm()
            }

            is NutrientsFormEvent.OnFatsChange -> {
                _uiState.value = _uiState.value.copy(fats = event.fats)
                validateForm()
            }

            is NutrientsFormEvent.OnCarbsChange -> {
                _uiState.value = _uiState.value.copy(carbs = event.carbs)
                validateForm()
            }

            is NutrientsFormEvent.OnProteinsChange -> {
                _uiState.value = _uiState.value.copy(proteins = event.proteins)
                validateForm()
            }

            is NutrientsFormEvent.OnWeightChange -> {
                _uiState.value = _uiState.value.copy(weight = event.weight)
                validateForm()
            }

            NutrientsFormEvent.OnAddIngredientClick -> {
                if (_uiState.value.isFormValid) {
                    val ingredient = createNewIngredient()
                    viewModelScope.launch {
                        _ingredientAdded.emit(ingredient)
                    }
                    resetForm()
                }
            }

            NutrientsFormEvent.OnNextStepClick -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.NavigateToAddTotalWeight)
                }
            }

            NutrientsFormEvent.OnBackClick -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.NavigateToBack)
                }
            }
        }
    }

    private fun validateForm() {
        _uiState.value = _uiState.value.copy(
            isFormValid = _uiState.value.ingredientName.isNotBlank() &&
                    _uiState.value.fats.isNotBlank() &&
                    _uiState.value.carbs.isNotBlank() &&
                    _uiState.value.proteins.isNotBlank() &&
                    _uiState.value.kkal.isNotBlank() &&
                    _uiState.value.weight.isNotBlank()
        )
    }

    private fun createNewIngredient(): Ingredient {
        return Ingredient(
            id = UUID.randomUUID().toString(),
            name = _uiState.value.ingredientName,
            kcal = _uiState.value.kkal.toDoubleOrNull() ?: 0.0,
            fats = _uiState.value.fats.toDoubleOrNull() ?: 0.0,
            carbs = _uiState.value.carbs.toDoubleOrNull() ?: 0.0,
            proteins = _uiState.value.proteins.toDoubleOrNull() ?: 0.0,
            weight = _uiState.value.weight.toDoubleOrNull() ?: 0.0,
            dishId = System.currentTimeMillis().toString()
        )
    }

    private fun resetForm() {
        _uiState.value = NutrientsFormState()
    }
}