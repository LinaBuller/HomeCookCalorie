package com.buller.ckkal.ui.screens.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.CustomOutlinedTextField
import com.buller.ckkal.ui.screens.DeleteDialog
import com.buller.ckkal.ui.screens.home.AddIngredientBottomSheetDialog
import com.buller.ckkal.ui.screens.home.EditIngredientDialog
import com.buller.ckkal.ui.screens.home.ListOfIngredient
import com.buller.ckkal.ui.screens.saved.roller.NumericRoller
import com.buller.ckkal.ui.screens.states.DishState
import com.buller.ckkal.ui.screens.states.IngredientsState

@Composable
fun DishEditScreenRoute(
    modifier: Modifier = Modifier,
    dish: Dish,
    dishEditViewModel: DishEditViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(dish) {
        dishEditViewModel.initDish(dish)
    }

    val onSaveDish = {
        dishEditViewModel.updateOldDish()
    }

    val onDeleteIngredient = { ingredient: Ingredient ->
        dishEditViewModel.removeIngredient(ingredient)
        dishEditViewModel.calculateCurrentDish()
    }

    val onEditIngredient = { ingredient: Ingredient ->
        dishEditViewModel.editIngredient(ingredient)
        dishEditViewModel.calculateCurrentDish()
    }
    val onSetWeight = { weight: Double ->
        if (weight > 0) {
            dishEditViewModel.setWeight(weight)
            dishEditViewModel.calculateCurrentDish()
        }
    }

    DishEditScreen(
        modifier = modifier,
        onSetWeight = onSetWeight,
        onSaveDish = onSaveDish,
        onDeleteIngredient = onDeleteIngredient,
        onEditIngredient = onEditIngredient,
        onBack = onBack
    )
}

@Composable
fun DishEditScreen(
    modifier: Modifier = Modifier,
    dishEditViewModel: DishEditViewModel = hiltViewModel(),
    onSetWeight: (Double) -> Unit,
    onBack: () -> Unit,
    onSaveDish: () -> Unit,
    onDeleteIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit
) {
    var isDeleteIngredientDialogOpen by remember { mutableStateOf(false) }
    var ingredientToDelete by remember { mutableStateOf<Ingredient?>(null) }

    var isEditIngredientDialogOpen by remember { mutableStateOf(false) }
    var ingredientToEdit by remember { mutableStateOf<Ingredient?>(null) }

    val ingredientsState = dishEditViewModel.ingredientsState.collectAsState()
    val dishState = dishEditViewModel.dish.collectAsState()

    var isAddIngredientDialogOpen by remember { mutableStateOf(false) }

    val setIngredient = { ingredient: Ingredient ->
        dishEditViewModel.setIngredient(ingredient)
        dishEditViewModel.calculateCurrentDish()
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.cancel)
                )
            }
            Text(text = stringResource(R.string.edit_dish), modifier = Modifier.weight(1f))
            IconButton(onClick = { isAddIngredientDialogOpen = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_ingredient)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DishPart(state = dishState.value, setWeight = { weight ->
            if (weight != null) {
                onSetWeight(weight)
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        ListOfIngredient(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
            ingredients = ingredientsState.value.ingredients,
            onRemoveIngredient = { ingredient ->
                isDeleteIngredientDialogOpen = true
                ingredientToDelete = ingredient
            },
            onEditIngredient = { ingredient ->
                isEditIngredientDialogOpen = true
                ingredientToEdit = ingredient
            })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(60.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.cancel),
                    tint = LocalContentColor.current
                )
                Text(text = stringResource(R.string.cancel))
            }
            Button(
                onClick = { onSaveDish() },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                contentPadding = PaddingValues(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
                )
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.save),
                    tint = LocalContentColor.current
                )
                Text(text = stringResource(R.string.save))
            }
        }
    }
    if (isDeleteIngredientDialogOpen && ingredientToDelete != null) {
        DeleteDialog(item = ingredientToDelete!!,
            confirmationText = stringResource(R.string.delete),
            title = stringResource(R.string.delete_ingredient),
            onConfirm = { ingredient ->
                onDeleteIngredient(ingredient)
                isDeleteIngredientDialogOpen = false
                ingredientToDelete = null
            },
            onDismiss = {
                isDeleteIngredientDialogOpen = false
            })
    }

    if (isEditIngredientDialogOpen && ingredientToEdit != null) {
        EditIngredientDialog(ingredient = ingredientToEdit!!, onSave = { editedIngredient ->
            onEditIngredient(editedIngredient)
            isEditIngredientDialogOpen = false
            ingredientToEdit = null
        }, onDismiss = {
            isEditIngredientDialogOpen = false
        })
    }

    if (isAddIngredientDialogOpen) {
        AddIngredientBottomSheetDialog(
            onDismiss = { isAddIngredientDialogOpen = false },
            onAddIngredient = { ingredient ->
                setIngredient(ingredient)
                isAddIngredientDialogOpen = false
            })
    }
}

@Composable
fun DishPart(state: DishState, setWeight: (Double?) -> Unit = {}) {

    var weight by remember { mutableStateOf(state.totalWeight.toString()) }

    LaunchedEffect(state.totalWeight) {
        weight = state.totalWeight.toString()
    }

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = state.name)
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.kkal))
                    NumericRoller(number = state.finalKcal)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.fats))
                    NumericRoller(number = state.finalFats)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.carbs))
                    NumericRoller(number = state.finalCarbs)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.proteins))
                    NumericRoller(number = state.finalProteins)
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            CustomOutlinedTextField(value = weight, onValueChange = {
                if (it.isEmpty()) {
                    weight = it
                    setWeight(null)
                } else {
                    val newWeight = it.toDoubleOrNull()
                    if (newWeight != null && newWeight >= 0) {
                        weight = it
                        setWeight(newWeight)
                    }
                }
            }, label = stringResource(R.string.weight), isNumeric = true,
                maxLength = 6,
                onShowError = {

                }, onNext = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DishEditScreenPreview() {
    val ingredients = listOf(
        Ingredient(
            ingredientId = "1",
            name = "Ingredient 1",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4,
            dishId = "1"
        ), Ingredient(
            ingredientId = "2",
            name = "Ingredient 2",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4,
            dishId = "2"
        ), Ingredient(
            ingredientId = "3",
            name = "Ingredient 3",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4,
            dishId = "3"
        )
    )
    val ingredientsState = IngredientsState(ingredients = ingredients)
    val dishState = DishState(
        name = "Блинчик",
        totalWeight = 230.0,
        finalFats = 0.0,
        finalKcal = 0.0,
        finalProteins = 0.0,
        finalCarbs = 0.0
    )
//    DishEditScreen(
//        ingredientsState = ingredientsState,
//        dishState = dishState,
//        onEditIngredient = {},
//        onSetWeight = { 0.0 },
//        onSaveDish = {},
//        onBack = {},
//        onDeleteIngredient = {}
//    )
}