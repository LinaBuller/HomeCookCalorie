package com.buller.ckkal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.buller.ckkal.ui.screens.CustomOutlinedTextField
import com.buller.ckkal.ui.screens.states.NutrientEditState
import com.buller.ckkal.ui.screens.states.OnNutrientChange

@Composable
fun EditIngredientDialog(
    ingredient: Ingredient, onSave: (Ingredient) -> Unit, onDismiss: () -> Unit
) {
    var state by remember {
        mutableStateOf(
            NutrientEditState(
                name = ingredient.name.toString(),
                fats = ingredient.fats.toString(),
                kcal = ingredient.kcal.toString(),
                carbs = ingredient.carbs.toString(),
                proteins = ingredient.proteins.toString(),
                weight = ingredient.weight.toString()
            )
        )
    }

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.edit_ingredient)) },
        text = {
            NutrientEditForm(state = state, onNutrientChange = { field, value ->
                state = when (field) {
                    "name" -> state.copy(name = value)
                    "fats" -> state.copy(fats = value)
                    "kcal" -> state.copy(kcal = value)
                    "carbs" -> state.copy(carbs = value)
                    "proteins" -> state.copy(proteins = value)
                    "weight" -> state.copy(weight = value)
                    else -> state
                }
            })
        },
        confirmButton = {
            Button(onClick = {
                val editedIngredient = ingredient.copy(
                    name = state.name,
                    kcal = state.kcal.toDoubleOrNull() ?: 0.0,
                    fats = state.fats.toDoubleOrNull() ?: 0.0,
                    carbs = state.carbs.toDoubleOrNull() ?: 0.0,
                    proteins = state.proteins.toDoubleOrNull() ?: 0.0,
                    weight = state.weight.toDoubleOrNull() ?: 0.0
                )
                onSave(editedIngredient)
            }) {
                Text(text = stringResource(R.string.save))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        })
}


@Composable
fun NutrientEditForm(
    state: NutrientEditState, onNutrientChange: OnNutrientChange
) {
    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterFats = remember { FocusRequester() }
    val focusRequesterCarbs = remember { FocusRequester() }
    val focusRequesterProteins = remember { FocusRequester() }
    val focusRequesterKkal = remember { FocusRequester() }
    val focusRequesterWeight = remember { FocusRequester() }

    Column {
        CustomOutlinedTextField(value = state.name,
            onValueChange = {
                onNutrientChange("name", it)
            },
            label = stringResource(R.string.name_ingredient),
            isNumeric = false,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterName),
            maxLength = 20,
            onShowError = {

            },
            onNext = {
                focusRequesterFats.requestFocus()
            })
        Spacer(modifier = Modifier.height(8.dp))
        CustomOutlinedTextField(value = state.fats,
            onValueChange = { onNutrientChange("fats", it) },
            label = stringResource(R.string.fats),
            isNumeric = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterFats),
            maxLength = 6,
            onShowError = {

            },
            onNext = {
                focusRequesterCarbs.requestFocus()
            })
        Spacer(modifier = Modifier.height(8.dp))
        CustomOutlinedTextField(value = state.carbs,
            onValueChange = { onNutrientChange("carbs", it) },
            label = stringResource(R.string.carbs),
            isNumeric = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterCarbs),
            maxLength = 6,
            onShowError = {

            },
            onNext = {
                focusRequesterProteins.requestFocus()
            })
        Spacer(modifier = Modifier.height(8.dp))
        CustomOutlinedTextField(value = state.proteins,
            onValueChange = { onNutrientChange("proteins", it) },
            label = stringResource(R.string.proteins),
            isNumeric = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterProteins),
            maxLength = 6,
            onShowError = {

            },
            onNext = {
                focusRequesterKkal.requestFocus()
            })
        Spacer(modifier = Modifier.height(8.dp))
        CustomOutlinedTextField(value = state.kcal,
            onValueChange = { onNutrientChange("kcal", it) },
            label = stringResource(R.string.kkal),
            isNumeric = false,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterKkal),
            maxLength = 6,
            onShowError = {

            },
            onNext = {
                focusRequesterWeight.requestFocus()
            })
        Spacer(modifier = Modifier.height(8.dp))
        CustomOutlinedTextField(value = state.weight,
            onValueChange = { onNutrientChange("weight", it) },
            label = stringResource(R.string.weight),
            isNumeric = false,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterWeight),
            maxLength = 6,
            onShowError = {

            },
            onNext = {})
    }
}

@Preview(showBackground = true)
@Composable
fun EditIngredientDialogPreview() {
    val ingredient = Ingredient(
        ingredientId = "1",
        name = "Ingredient 1",
        fats = 123.9,
        kcal = 2.5,
        carbs = 2.3,
        proteins = 4.5,
        weight = 234.4
    )
    EditIngredientDialog(ingredient = ingredient, onSave = {}, onDismiss = {})
}