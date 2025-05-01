package com.buller.ckkal.ui.screens.saved

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.LabeledValue
import com.buller.ckkal.ui.theme.CKkalTheme


@Composable
fun DishView(
    dish: Dish,
    onRemoveDish: (Dish) -> Unit,
    onEditDish: (Dish) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 4.dp)
            .clickable { onEditDish(dish) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dish.name.uppercase(),
                    fontSize = 23.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    modifier = Modifier.weight(1f),
                )
                Row {
                    IconButton(onClick = { onEditDish(dish) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                    IconButton(onClick = { onRemoveDish(dish) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                LabeledValue(
                    value = dish.allFats.toString(),
                    label = R.string.fats,
                    styleColor = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))
                LabeledValue(
                    value = dish.allKcal.toString(),
                    label = R.string.kkal,
                    styleColor = MaterialTheme.colorScheme.onPrimary
                )
            }
            Column(horizontalAlignment = Alignment.Start) {
                LabeledValue(
                    value = dish.allCarbs.toString(),
                    label = R.string.carbs,
                    styleColor = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))
                LabeledValue(
                    value = dish.allWeight.toString(),
                    label = R.string.weight,
                    styleColor = MaterialTheme.colorScheme.onPrimary
                )
            }

            Column(horizontalAlignment = Alignment.Start) {
                LabeledValue(
                    value = dish.allProteins.toString(),
                    label = R.string.proteins,
                    styleColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {


    val listIngredient = listOf(
        Ingredient(
            "1",
            name = "Ingredient 1",
            dishId = "",
            kcal = 123.9,
            proteins = 2.5,
            carbs = 2.3,
            fats = 4.5,
            weight = 234.4
        ), Ingredient(
            "2",
            name = "Ingredient 2",
            dishId = "",
            kcal = 123.9,
            proteins = 2.5,
            carbs = 2.3,
            fats = 4.5,
            weight = 234.4
        )
    )
    val dish = Dish(
        name = "Tasty Dish",
        ingredients = listIngredient,
        id = "d",
        allFats = 0.9,
        allCarbs = 0.9,
        allKcal = 0.9,
        allProteins = 0.9,
        allWeight = 0.9
    )
    CKkalTheme {
        DishView(dish, onRemoveDish = {}, onEditDish = {})
    }
}

