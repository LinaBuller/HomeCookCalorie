package com.buller.ckkal.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.domain.utils.MathUtil
import com.buller.ckkal.ui.screens.CustomOutlinedTextField
import com.buller.ckkal.ui.screens.saved.roller.NumericRoller

//сделать в теме стили шрифтов
// сделать разные стили для карточек
// сохранять ингредиенты на первом экране


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientBottomSheetDialog(onDismiss: () -> Unit, onAddIngredient: (Ingredient) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxSize(),
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        NutrientsForm(onDismiss = onDismiss, onAddIngredient = onAddIngredient)
    }
}

@Composable
fun NutrientsForm(onDismiss: () -> Unit, onAddIngredient: (Ingredient) -> Unit) {

    var ingredientName by remember { mutableStateOf("") }
    var kkal by remember { mutableStateOf("") }
    var fats by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var proteins by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterFats = remember { FocusRequester() }
    val focusRequesterCarbs = remember { FocusRequester() }
    val focusRequesterProteins = remember { FocusRequester() }
    val focusRequesterKkal = remember { FocusRequester() }
    val focusRequesterWeight = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    var isRightCalculateNutrients by remember { mutableStateOf(false) }

    fun createNewIngredient(): Ingredient {
        return Ingredient(
            name = ingredientName,
            kcal = kkal.toDoubleOrNull() ?: 0.0,
            fats = fats.toDoubleOrNull() ?: 0.0,
            carbs = carbs.toDoubleOrNull() ?: 0.0,
            proteins = proteins.toDoubleOrNull() ?: 0.0,
            weight = weight.toDoubleOrNull() ?: 0.0,
            dishId = System.currentTimeMillis().toString()
        )
    }

    LaunchedEffect(Unit) {
        focusRequesterName.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            CustomOutlinedTextField(value = ingredientName,
                onValueChange = { ingredientName = it },
                label = stringResource(R.string.name_ingredient),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterName),
                maxLength = 20,
                onShowError = {

                },
                onNext = {
                    focusRequesterFats.requestFocus()
                })

            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Column(modifier = Modifier.fillMaxWidth(0.6f))
                {
                    CustomOutlinedTextField(value = fats,
                        onValueChange = {
                            fats = it
                            isRightCalculateNutrients = true
                        },
                        label = stringResource(R.string.fats),
                        modifier = Modifier.focusRequester(focusRequesterFats),
                        isNumeric = true,
                        maxLength = 5,
                        onShowError = {

                        },
                        onNext = {
                            focusRequesterCarbs.requestFocus()
                        })
                    CustomOutlinedTextField(value = carbs,
                        onValueChange = {
                            carbs = it
                        },
                        label = stringResource(R.string.carbs),
                        modifier = Modifier.focusRequester(focusRequesterCarbs),
                        isNumeric = true,
                        maxLength = 5,
                        onShowError = {

                        },
                        onNext = {
                            focusRequesterProteins.requestFocus()
                        })
                    CustomOutlinedTextField(value = proteins,
                        onValueChange = {
                            proteins = it
                        },
                        label = stringResource(R.string.proteins),
                        modifier = Modifier.focusRequester(focusRequesterProteins),
                        isNumeric = true,
                        maxLength = 5,
                        onShowError = {

                        },
                        onNext = {
                            focusRequesterKkal.requestFocus()
                        })
                }

                CalculateWarning(fats = fats.toDoubleOrNull() ?: 0.0,
                    proteins = proteins.toDoubleOrNull() ?: 0.0,
                    carbs = carbs.toDoubleOrNull() ?: 0.0,
                    setCalculateCalories = { countedCalories ->
                        kkal = countedCalories.toString()
                    })
            }
            Row {
                CustomOutlinedTextField(value = kkal,
                    onValueChange = { kkal = it },
                    label = stringResource(R.string.kkal),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .focusRequester(focusRequesterKkal),
                    isNumeric = true,
                    maxLength = 5,
                    onShowError = {

                    },
                    onNext = {
                        focusRequesterWeight.requestFocus()
                    })
                CustomOutlinedTextField(value = weight,
                    onValueChange = { weight = it },
                    label = stringResource(R.string.weight),
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequesterWeight),
                    isNumeric = true,
                    maxLength = 5,
                    onShowError = {

                    },
                    onNext = {
                        keyboardController?.hide()
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.comment_calc_calories))
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(16.dp),
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel))
            }

            Button(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                onClick = {
                    onAddIngredient(createNewIngredient())
                    ingredientName = ""
                    kkal = ""
                    fats = ""
                    carbs = ""
                    proteins = ""
                    weight = ""
                },
                enabled = ingredientName.isNotBlank() && fats.isNotBlank() && carbs.isNotBlank() && proteins.isNotBlank() && weight.isNotBlank()
            ) {
                Text("Add")
            }
        }
    }
}

@Composable
fun CalculateWarning(
    fats: Double, proteins: Double, carbs: Double, setCalculateCalories: (Double) -> Unit
) {
    val calories = (fats * 9.0) + (proteins * 4.0) + (carbs * 4.0)
    val roundedCalories = MathUtil.roundToDouble(calories)
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column() {
                Text(text = "We counted the calories for you.")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(R.string.rec_calories))
                NumericRoller(number = roundedCalories)
                Spacer(modifier = Modifier.height(4.dp))
            }
            Button(onClick = {
                setCalculateCalories.invoke(roundedCalories)
            }) {
                Text(text = "Use this calories")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NutrientsFormPreview() {
    NutrientsForm(onDismiss = {}, onAddIngredient = {})
}