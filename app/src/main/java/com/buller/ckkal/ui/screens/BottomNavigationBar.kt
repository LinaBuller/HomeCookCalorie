package com.buller.ckkal.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Dish
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    val items = listOf(
        BottomNavItem.HomeItem,
        BottomNavItem.SavedDishesItem,
        BottomNavItem.ProfileItem
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            Log.d("BottomNavItem", "Item: ${item.label}")
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.label)
                    )
                },
                label = {
                    Text(text = stringResource(item.label))
                },
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                alwaysShowLabel = true
            )
        }
    }
}

sealed class BottomNavItem(
    val icon: ImageVector,
    val label: Int,
    val route: String
) {
    data object HomeItem :
        BottomNavItem(icon = Icons.Filled.Home, label = R.string.home, route = "home")

    data object SavedDishesItem :
        BottomNavItem(icon = Icons.Filled.Star, label = R.string.saved_dishes, route = "dishes")

    data object ProfileItem :
        BottomNavItem(icon = Icons.Filled.Person, label = R.string.profile, route = "profile")
}

sealed class DeepNavItem(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    data object CalculatesItem : DeepNavItem(route = "calculations")
    data object EditDishItem :
        DeepNavItem(
            route = "editDish/{$DISH_ARGUMENT}",
            arguments = listOf(navArgument(DISH_ARGUMENT) {
                type = NavType.StringType
            })
        ) {

        fun createRoute(dish: Dish): String {
            return "editDish/${Uri.encode(Json.encodeToString(dish))}"
        }
    }

    companion object {
        const val DISH_ARGUMENT = "dish"
    }
}


