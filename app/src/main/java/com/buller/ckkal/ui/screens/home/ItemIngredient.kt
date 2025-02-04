package com.buller.ckkal.ui.screens.home


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
import com.buller.ckkal.domain.entities.Ingredient

@Composable
fun ItemRow(
    modifier: Modifier = Modifier,
    item: Ingredient,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
) {
    Card(
        modifier = modifier.padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.background(Color.LightGray)
        ) {
            Row(
                modifier = modifier.padding(5.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.name.toString(), modifier = modifier.weight(1f))
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.kkal))
                    Text(text = item.kcal.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.fats))
                    Text(text = item.fats.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.carbs))
                    Text(text = item.carbs.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.proteins))
                    Text(text = item.proteins.toString())
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.weight))
                    Text(text = item.weight.toString())
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowPreview() {
    val ingredient = Ingredient(
        ingredientId = "1",
        name = "Ingredient 1",
        fats = 123.9,
        kcal = 2.5,
        carbs = 2.3,
        proteins = 4.5,
        weight = 234.4
    )
    ItemRow(item = ingredient, onRemoveIngredient = {}, onEditIngredient = {})
}
