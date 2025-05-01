package com.buller.ckkal.ui.screens.home.views

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Ingredient
import com.buller.ckkal.ui.screens.LabeledValue

@Composable
fun CollapsibleCard(
    modifier: Modifier = Modifier,
    item: Ingredient,
    styleColor: Pair<Color, Color>,
    onRemoveIngredient: (Ingredient) -> Unit,
    onEditIngredient: (Ingredient) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                onClick = {
                    isExpanded = !isExpanded
                }, indication = null
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = styleColor.second),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                    text = item.name.toString().uppercase(),
                    fontSize = 23.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    modifier = Modifier.weight(1f),
                    color = styleColor.first
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
                        .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        LabeledValue(
                            value = item.fats.toString(),
                            label = R.string.fats,
                            styleColor = styleColor.first
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LabeledValue(
                            value = item.kcal.toString(),
                            label = R.string.kkal,
                            styleColor = styleColor.first
                        )
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        LabeledValue(
                            value = item.carbs.toString(),
                            label = R.string.carbs,
                            styleColor = styleColor.first
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LabeledValue(
                            value = item.weight.toString(),
                            label = R.string.weight,
                            styleColor = styleColor.first
                        )
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        LabeledValue(
                            value = item.proteins.toString(),
                            label = R.string.proteins,
                            styleColor = styleColor.first
                        )
                    }
                }
            }
        }
    }
}