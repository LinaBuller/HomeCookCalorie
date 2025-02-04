package com.buller.ckkal.ui.screens.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.ui.screens.DeleteDialog
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.states.DishesState
import com.buller.ckkal.R

@Composable
fun SavedDishesRoute(
    modifier: Modifier = Modifier, sharedViewModel: SharedViewModel = hiltViewModel(),
    navigateToEditDishScreen: (Dish) -> Unit
) {
    val state by sharedViewModel.savedDishesState.collectAsStateWithLifecycle()

    val onRemoveDish = { dish: Dish ->
        sharedViewModel.deleteDish(dish)
    }

    SavedDishesScreen(
        modifier = modifier,
        state = state,
        onRemoveDish = onRemoveDish,
        onEditDish = navigateToEditDishScreen
    )
}

@Composable
fun SavedDishesScreen(
    modifier: Modifier = Modifier,
    state: DishesState,
    onRemoveDish: (Dish) -> Unit,
    onEditDish: (Dish) -> Unit
) {
    val dishes = state.dishes

    var isDeleteDishDialogOpen by remember { mutableStateOf(false) }
    var dishToDelete by remember { mutableStateOf<Dish?>(null) }

    if (dishes.isNotEmpty()) {
        ListOfDishes(modifier = Modifier.fillMaxWidth(), dishes = dishes, onRemoveDish = {
            dishToDelete = it
            isDeleteDishDialogOpen = true
            onRemoveDish.invoke(it)
        }, onEditDish = onEditDish)
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No ingredients available",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    if (isDeleteDishDialogOpen) {
        DeleteDialog(item = dishToDelete!!,
            title = stringResource(R.string.delete_dish),
            confirmationText = stringResource(R.string.delete),
            onConfirm = {
                onRemoveDish(it)
                isDeleteDishDialogOpen = false
            },
            onDismiss = { isDeleteDishDialogOpen = false })
    }

//    if (isEditDishDialogOpen) {
//        DishEditScreen(dish = dishToEdit!!,
//            onSaveDish = onEditDish,
//            onDismiss = {
//                isEditDishDialogOpen = false
//            }
//        )
//    }
}

@Composable
fun ListOfDishes(
    modifier: Modifier = Modifier,
    dishes: List<Dish>,
    onRemoveDish: (Dish) -> Unit,
    onEditDish: (Dish) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(dishes) { item ->
            ItemDish(dish = item, onRemoveDish = onRemoveDish, onEditDish = onEditDish)
        }
    }
}