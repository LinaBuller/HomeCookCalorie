package com.buller.ckkal.ui.screens.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.buller.ckkal.R

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentTab: BottomNavItem,
    onTabSelected: (BottomNavItem) -> Unit
) {
    val items = listOf(
        BottomNavItem.HomeItem, BottomNavItem.SavedDishesItem, BottomNavItem.InfoItem
    )
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface
            ), containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { navItem ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(navItem.label),
                    )
                }, label = {
                    Text(
                        text = stringResource(navItem.label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }, selected = currentTab == navItem,
                onClick = {
                    onTabSelected(navItem)
                }, alwaysShowLabel = true, colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


sealed class BottomNavItem(
    val icon: ImageVector, val label: Int, val route: String
) {
    data object HomeItem :
        BottomNavItem(icon = Icons.Filled.Home, label = R.string.home, route = HOME)

    data object SavedDishesItem : BottomNavItem(
        icon = Icons.Filled.Star, label = R.string.saved_dishes, route = SAVED_DISHES
    )

    data object InfoItem : BottomNavItem(
        icon = Icons.Filled.Info, label = R.string.info, route = INFO
    )

    companion object BottomNavRoutes {
        const val HOME = "home"
        const val SAVED_DISHES = "dishes"
        const val INFO = "info"
    }
}

sealed class DialogNavItem(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    data object AddIngredientItem : DialogNavItem(
        route = "$ADD_INGREDIENT_ROUTE?$IS_EDITING_INGREDIENT_ARGUMENT={$IS_EDITING_INGREDIENT_ARGUMENT}",
        arguments = listOf(navArgument(IS_EDITING_INGREDIENT_ARGUMENT) {
            type = NavType.BoolType
            defaultValue = false
        })
    ) {
        fun createRoute(isEditing: Boolean): String {
            return "$ADD_INGREDIENT_ROUTE?$IS_EDITING_INGREDIENT_ARGUMENT=$isEditing"
        }
    }

    data object TotalWeightItem : DialogNavItem(
        route = "$ADD_ALL_WEIGHT_ROUTE?$OLD_WEIGHT_ARGUMENT={$OLD_WEIGHT_ARGUMENT}",
        arguments = listOf(
            navArgument(OLD_WEIGHT_ARGUMENT) {
                type = NavType.StringType
                nullable = true
            })
    ) {
        fun createRoute(oldWeight: Double? = null): String {
            val oldWeightParam = oldWeight?.toString()
            return "$ADD_ALL_WEIGHT_ROUTE?$OLD_WEIGHT_ARGUMENT=$oldWeightParam"
        }
    }

    data object CalculateItem : DialogNavItem(route = CALCULATE_ROUTE)
    data object EditDishItem : DialogNavItem(route = EDIT_DISH_ROUTE)

    companion object {
        const val ADD_INGREDIENT_ROUTE = "add_ingredient_route"
        const val ADD_ALL_WEIGHT_ROUTE = "add_all_weight_route"
        const val CALCULATE_ROUTE = "calculate_route"
        const val EDIT_DISH_ROUTE = "editDish"
        const val DISH_ARGUMENT = "dish"
        const val OLD_WEIGHT_ARGUMENT = "old_weight"
        const val IS_EDITING_INGREDIENT_ARGUMENT = "is_editing_ingredient"
        const val IS_EDITING_WEIGHT_ARGUMENT = "is_editing_weight"
    }
}