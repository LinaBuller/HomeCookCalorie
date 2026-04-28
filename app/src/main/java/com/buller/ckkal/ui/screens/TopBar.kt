package com.buller.ckkal.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.views.BottomNavItem
import com.buller.ckkal.ui.screens.views.DialogNavItem

@Composable
fun TopBar(
    navController: NavHostController,
    currentRoute: String?,
    onSearch: () -> Unit = {},
    navigateToAddIngredientScreen: () -> Unit = {}
) {
    when (currentRoute) {
        DialogNavItem.AddIngredientItem.route -> {
            CustomTopAppBar(
                title = stringResource(R.string.new_ingredient),
                navController = navController,
                showBackButton = true
            )
        }

        DialogNavItem.TotalWeightItem.route -> {
            CustomTopAppBar(
                title = stringResource(R.string.total_weight),
                navController = navController,
                showBackButton = true
            )
        }

        DialogNavItem.CalculateItem.route -> {
            CustomTopAppBar(
                title = stringResource(R.string.your_dish),
                navController = navController,
                showBackButton = true
            )
        }

        BottomNavItem.SavedDishesItem.route -> {
            CustomTopAppBar(
                title = stringResource(R.string.saved_dishes),
                navController = navController,
                onActions = {
                    //TODO
//                    IconButton(onClick = onSearch) {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = stringResource(R.string.search)
//                        )
//                    }
                }
            )
        }

        DialogNavItem.EditDishItem.route -> {
            CustomTopAppBar(
                title = stringResource(R.string.edit_dish),
                navController = navController,
                showBackButton = true,
                onActions = {
                    FilledIconButton(onClick = navigateToAddIngredientScreen) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.cancel)
                        )
                    }
                }
            )
        }

        BottomNavItem.InfoItem.route -> {
            CustomTopAppBar(
                title = stringResource(R.string.info),
                navController = navController
            )
        }

        else -> {
            CustomTopAppBar(
                title = stringResource(R.string.your_ingredients),
                navController = navController,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    navController: NavHostController,
    showBackButton: Boolean = false,
    onActions: @Composable () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.to_back)
                    )
                }
            }
        },
        actions = { onActions.invoke() })
}