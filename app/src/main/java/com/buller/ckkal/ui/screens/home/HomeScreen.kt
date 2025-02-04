package com.buller.ckkal.ui.screens.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.DeleteDialog
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.states.IngredientsState
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    onNavigateToCalculations: () -> Unit
) {
    val state by sharedViewModel.ingredientsState.collectAsStateWithLifecycle()

    val navigateToCalculations = {
        sharedViewModel.calculateCurrentDish()
        onNavigateToCalculations.invoke()
    }

    val onRemoveIngredient = { ingredient: Ingredient ->
        sharedViewModel.removeIngredient(ingredient)
    }

    val onEditIngredient = { ingredient: Ingredient ->
        sharedViewModel.editIngredient(ingredient)
    }

    HomeScreen(
        modifier = modifier,
        state = state,
        setIngredient = { ingredient -> sharedViewModel.setIngredient(ingredient) },
        setWeight = { weight ->
            sharedViewModel.setWeight(weight)
        },
        onNavigateToCalculations = navigateToCalculations,
        onRemoveIngredient = onRemoveIngredient,
        onEditIngredient = onEditIngredient
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: IngredientsState,
    setIngredient: (Ingredient) -> Unit,
    setWeight: (Double) -> Unit,
    onNavigateToCalculations: () -> Unit,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit
) {
    val ingredients: List<Ingredient> = state.ingredients
    var isAddIngredientDialogOpen by remember { mutableStateOf(false) }
    var isAllWeightDialogOpen by remember { mutableStateOf(false) }
    var isDeleteIngredientDialogOpen by remember { mutableStateOf(false) }
    var isEditIngredientDialogOpen by remember { mutableStateOf(false) }
    var ingredientToEdit by remember { mutableStateOf<Ingredient?>(null) }
    var ingredientToDelete by remember { mutableStateOf<Ingredient?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        if (ingredients.isNotEmpty()) {
            ListOfIngredient(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                ingredients = ingredients,
                onRemoveIngredient = {
                    ingredientToDelete = it
                    isDeleteIngredientDialogOpen = true
                },
                onEditIngredient = {
                    ingredientToEdit = it
                    isEditIngredientDialogOpen = true
                })
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_ingredient),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(60.dp)
        ) {
            Button(
                onClick = { isAddIngredientDialogOpen = true },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_ingredient),
                    tint = LocalContentColor.current
                )
                Text(
                    text = stringResource(R.string.add_ingredient)
                )
            }
            Button(
                onClick = { isAllWeightDialogOpen = true },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                contentPadding = PaddingValues(16.dp),
                enabled = ingredients.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (ingredients.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray,
                    contentColor = Color.White
                )
            ) {

                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.next_step),
                    tint = LocalContentColor.current
                )
                Text(
                    text = stringResource(R.string.next_step),
                )
            }
        }

    }

    if (isAddIngredientDialogOpen) {
        AddIngredientBottomSheetDialog(onDismiss = { isAddIngredientDialogOpen = false },
            onAddIngredient = { ingredient ->
                setIngredient(ingredient)
                isAddIngredientDialogOpen = false
            })
    }

    if (isAllWeightDialogOpen) {
        AllWeightDishDialog(onDismiss = { isAllWeightDialogOpen = false },
            onAddAllWeight = { weight ->
                setWeight(weight)
                isAllWeightDialogOpen = false
                onNavigateToCalculations.invoke()
            })
    }

    if (isDeleteIngredientDialogOpen) {
        DeleteDialog(item = ingredientToDelete!!,
            confirmationText = stringResource(R.string.delete),
            title = stringResource(R.string.delete_ingredient),
            onConfirm = {
                onRemoveIngredient(it)
                isDeleteIngredientDialogOpen = false
            },
            onDismiss = {
                isDeleteIngredientDialogOpen = false
            })
    }

    if (isEditIngredientDialogOpen && ingredientToEdit != null) {
        EditIngredientDialog(ingredient = ingredientToEdit!!, onSave = {
            onEditIngredient(it)
            isEditIngredientDialogOpen = false
        }, onDismiss = {
            isEditIngredientDialogOpen = false
        })
    }
}

@Composable
fun ListOfIngredient(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredient>,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val lastIndex by remember {
        derivedStateOf {
            ingredients.lastIndex
        }
    }

    LaunchedEffect(key1 = lastIndex) {
        if (lastIndex >= 0) {
            coroutineScope.launch {
                listState.animateScrollToItem(lastIndex)
            }
        }
    }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        itemsIndexed(ingredients, key = { _, item -> item.ingredientId }) { index, item ->

            CollapsibleCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (index * -32).dp),
                item = item,
                onRemoveIngredient = onRemoveIngredient,
                onEditIngredient = onEditIngredient
            )
        }
    }
}

@Composable
fun CollapsibleCard(
    modifier: Modifier = Modifier,
    item: Ingredient,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(modifier = modifier
        .clickable { isExpanded = !isExpanded }
        .padding(bottom = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name.toString().uppercase(),
                    style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )
                AnimatedVisibility(visible = !isExpanded) {
                    IconButton(onClick = { isExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                }
                AnimatedVisibility(visible = isExpanded) {
                    Row {
                        IconButton(onClick = { onEditIngredient(item) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(R.string.delete)
                            )
                        }
                        IconButton(onClick = { onRemoveIngredient(item) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete)
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 32.dp)
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = item.fats.toString(),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.fats),
                            style = TextStyle(fontSize = 16.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.kcal.toString(),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.kkal),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = item.carbs.toString(),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.carbs),
                            style = TextStyle(fontSize = 16.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.weight.toString(),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.weight),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = item.proteins.toString(),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.proteins),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val ingredients = listOf(
        Ingredient(
            dishId = "1",
            ingredientId = "1",
            name = "Ingredient 1",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4
        ),

        Ingredient(
            dishId = "2",
            ingredientId = "2",
            name = "Ingredient 2",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4
        ), Ingredient(
            dishId = "3",
            ingredientId = "3",
            name = "Ingredient 3",
            fats = 123.9,
            kcal = 2.5,
            carbs = 2.3,
            proteins = 4.5,
            weight = 234.4
        )
    )
    ListOfIngredient(ingredients = ingredients, onRemoveIngredient = {}, onEditIngredient = {})
//    val state = remember { IngredientsState(ingredients) }
//    HomeScreen(state = state,
//        setIngredient = {},
//        setWeight = {},
//        onNavigateToCalculations = {},
//        onRemoveIngredient = {},
//        onEditIngredient = {})
}

