package com.buller.ckkal.ui.screens.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.theme.CKkalTheme


@Composable
fun ItemDish(
    dish: Dish,
    onRemoveDish: (Dish) -> Unit,
    onEditDish: (Dish) -> Unit
) {
    Card(
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.LightGray)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = dish.name, modifier = Modifier.weight(1f))
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.kkal))
                    Text(text = dish.allKcal.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.fats))
                    Text(text = dish.allFats.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.carbs))
                    Text(text = dish.allCarbs.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.proteins))
                    Text(text = dish.allProteins.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.weight))
                    Text(text = dish.allWeight.toString())
                }
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
        itemId = "d",
        allFats = 0.9,
        allCarbs = 0.9,
        allKcal = 0.9,
        allProteins = 0.9,
        allWeight = 0.9
    )
    CKkalTheme {
        ItemDish(dish, onRemoveDish = {}, onEditDish = {})
    }
}

