package com.buller.ckkal.ui.screens.home.dialogs

import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.domain.utils.MathUtil
import com.buller.ckkal.ui.screens.CustomOutlinedTextField
import com.buller.ckkal.ui.screens.roller.NumericRoller


@Composable
fun AddIngredientDialog(
    onDismiss: () -> Unit, onAddIngredient: (Ingredient) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
        ) {
            NutrientsForm(onDismiss = onDismiss, onAddIngredient = onAddIngredient)
        }
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
            .padding(16.dp)
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CustomOutlinedTextField(
                value = ingredientName,
                onValueChange = { ingredientName = it },
                label = stringResource(R.string.ingredient_name),
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    CustomOutlinedTextField(
                        value = fats,
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
                    CustomOutlinedTextField(
                        value = carbs,
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
                    CustomOutlinedTextField(
                        value = proteins,
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

//                CalculateWarning(
//                    fats = fats.toDoubleOrNull() ?: 0.0,
//                    proteins = proteins.toDoubleOrNull() ?: 0.0,
//                    carbs = carbs.toDoubleOrNull() ?: 0.0,
//                    setCalculateCalories = { countedCalories ->
//                        kkal = countedCalories.toString()
//                    })
            }
            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                value = kkal,
                onValueChange = { kkal = it },
                label = stringResource(R.string.kkal),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterKkal),
                isNumeric = true,
                maxLength = 5,
                onShowError = {

                },
                onNext = {
                    focusRequesterWeight.requestFocus()
                })

            CustomOutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = stringResource(R.string.weight),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterWeight),
                isNumeric = true,
                maxLength = 5,
                onShowError = {

                },
                onNext = {
                    keyboardController?.hide()
                })

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.comment_calc_calories),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()

        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(16.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                shape = MaterialTheme.shapes.medium,
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
                Text(
                    text = stringResource(R.string.add), style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

//@Composable
//fun CalculateWarning(
//    fats: Double, proteins: Double, carbs: Double, setCalculateCalories: (Double) -> Unit
//) {
//    val calories = (fats * 9.0) + (proteins * 4.0) + (carbs * 4.0)
//    val roundedCalories = MathUtil.roundToDouble(calories)
//    Card(
//        modifier = Modifier
//            .padding(8.dp)
//            .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.medium),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.secondary
//        ),
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = stringResource(R.string.text_counted_calorie),
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                NumericRoller(number = roundedCalories)
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//            Button(
//                onClick = {
//                    setCalculateCalories.invoke(roundedCalories)
//                },
//                modifier = Modifier.fillMaxWidth(),
//                shape = MaterialTheme.shapes.medium,
//            ) {
//                Text(
//                    text = stringResource(R.string.use_calories),
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}