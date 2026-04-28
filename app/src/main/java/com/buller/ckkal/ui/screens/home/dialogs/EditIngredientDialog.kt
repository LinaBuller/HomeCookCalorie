package com.buller.ckkal.ui.screens.home.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.Dialog
import com.buller.ckkal.ui.screens.views.CustomOutlinedTextField
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

    val stateUpdaters = mapOf<String, (String) -> NutrientEditState>(
        NutrientEditState.NAME to { value -> state.copy(name = value) },
        NutrientEditState.FATS to { value -> state.copy(fats = value) },
        NutrientEditState.KCAL to { value -> state.copy(kcal = value) },
        NutrientEditState.CARBS to { value -> state.copy(carbs = value) },
        NutrientEditState.PROTEINS to { value -> state.copy(proteins = value) },
        NutrientEditState.WEIGHT to { value -> state.copy(weight = value) }
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                NutrientEditForm(state = state, onNutrientChange = { field, value ->
                    state = stateUpdaters[field]?.invoke(value) ?: state
                })
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(16.dp),
                        onClick = onDismiss
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(16.dp),
                        onClick = {
                            val editedIngredient = ingredient.copy(
                                name = state.name,
                                kcal = state.kcal.toDoubleOrNull() ?: 0.0,
                                fats = state.fats.toDoubleOrNull() ?: 0.0,
                                carbs = state.carbs.toDoubleOrNull() ?: 0.0,
                                proteins = state.proteins.toDoubleOrNull() ?: 0.0,
                                weight = state.weight.toDoubleOrNull() ?: 0.0
                            )
                            onSave(editedIngredient)
                            onDismiss.invoke()
                        }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
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
                onNutrientChange(NutrientEditState.NAME, it)
            },
            label = stringResource(R.string.ingredient_name),
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
            onValueChange = { onNutrientChange(NutrientEditState.FATS, it) },
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
            onValueChange = { onNutrientChange(NutrientEditState.CARBS, it) },
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
            onValueChange = { onNutrientChange(NutrientEditState.PROTEINS, it) },
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
            onValueChange = { onNutrientChange(NutrientEditState.KCAL, it) },
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
            onValueChange = { onNutrientChange(NutrientEditState.WEIGHT, it) },
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
        id = "1",
        name = "Ingredient 1",
        fats = 123.9,
        kcal = 2.5,
        carbs = 2.3,
        proteins = 4.5,
        weight = 234.4,
        dishId = ""
    )
    EditIngredientDialog(ingredient = ingredient, onSave = {}, onDismiss = {})
}

