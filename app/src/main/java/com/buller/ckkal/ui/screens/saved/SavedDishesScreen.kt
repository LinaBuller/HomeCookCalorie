package com.buller.ckkal.ui.screens.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.ui.screens.DeleteDialog
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.states.DishesState
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.views.DataOrPlaceholder

@Composable
fun SavedDishesRoute(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navigateToEditDishScreen: (Dish) -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val state by sharedViewModel.savedDishesState.collectAsStateWithLifecycle()

    val onRemoveDish = { dish: Dish ->
        sharedViewModel.deleteDish(dish)
    }

    SavedDishesScreen(
        modifier = modifier,
        state = state,
        onRemoveDish = onRemoveDish,
        onEditDish = navigateToEditDishScreen,
        navigateToHomeScreen = navigateToHomeScreen
    )
}

@Composable
fun SavedDishesScreen(
    modifier: Modifier = Modifier,
    state: DishesState,
    onRemoveDish: (Dish) -> Unit,
    onEditDish: (Dish) -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val dishes = state.dishes

    var isDeleteDishDialogOpen by remember { mutableStateOf(false) }
    var dishToDelete by remember { mutableStateOf<Dish?>(null) }
    val listState = rememberLazyListState()
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DataOrPlaceholder(
            modifier = modifier.weight(1f),
            title = R.string.no_saved_dishes,
            icon = R.drawable.dish_icon,
            isNotEmpty = dishes.isNotEmpty()
        ) {
            ListOfDishes(
                modifier = modifier.weight(1f).padding(top = 16.dp),
                dishes = dishes,
                state = listState,
                onRemoveDish = {
                    dishToDelete = it
                    isDeleteDishDialogOpen = true
                },
                onEditDish = onEditDish,
            )
        }
        if (dishes.isEmpty()) {
            Button(
                onClick = { navigateToHomeScreen() },
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(20.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = LocalContentColor.current
                ),
                shape = RoundedCornerShape(20.dp),
                enabled = false,
                contentPadding = PaddingValues(18.dp)
            ) {
                Text(
                    text = stringResource(R.string.start_add_ingredients_to_dish),
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalContentColor.current
                )
            }
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

    LaunchedEffect(dishes.size) {
        if (dishes.isNotEmpty()) {
            listState.animateScrollToItem(dishes.size - 1)
        }
    }
}

@Composable
fun ListOfDishes(
    modifier: Modifier = Modifier,
    dishes: List<Dish>,
    state: LazyListState,
    onRemoveDish: (Dish) -> Unit,
    onEditDish: (Dish) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        items(dishes, key = { it.id }) { item ->
            DishView(
                dish = item,
                onRemoveDish = onRemoveDish,
                onEditDish = onEditDish,
            )
        }
    }
}
