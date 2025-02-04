package com.buller.ckkal.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.ui.screens.home.CalculationRoute
import com.buller.ckkal.ui.screens.home.HomeRoute
import com.buller.ckkal.ui.screens.saved.DishEditScreenRoute
import com.buller.ckkal.ui.screens.saved.SavedDishesRoute
import kotlinx.serialization.json.Json

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.HomeItem) }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigationBar(selectedItem = selectedItem) { item ->
            if (navController.currentBackStackEntry?.destination?.route != item.route) {
                selectedItem = item
                navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }) { innerPadding ->
        NavHostContainer(
            navController = navController, modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        )
    }
}


@Composable
fun NavHostContainer(navController: NavHostController, modifier: Modifier = Modifier) {

    val sharedViewModel: SharedViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.HomeItem.route,
    ) {
        composable(BottomNavItem.HomeItem.route) {

            HomeRoute(
                modifier = modifier,
                sharedViewModel = sharedViewModel,
                onNavigateToCalculations = {
                    navController.navigate(DeepNavItem.CalculatesItem.route) {
                        popUpTo(BottomNavItem.HomeItem.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
        composable(BottomNavItem.SavedDishesItem.route) {
            SavedDishesRoute(
                modifier = modifier,
                sharedViewModel = sharedViewModel,
                navigateToEditDishScreen = { dish ->
                    navController.navigate(DeepNavItem.EditDishItem.createRoute(dish = dish)) {
                        popUpTo(BottomNavItem.HomeItem.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(BottomNavItem.ProfileItem.route) {
            ProfileScreen()
        }
        composable(DeepNavItem.CalculatesItem.route) {
            CalculationRoute(modifier = modifier, sharedViewModel = sharedViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onSavedDishesNav = {
                    navController.navigate(BottomNavItem.SavedDishesItem.route) {
                        popUpTo(BottomNavItem.HomeItem.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
        composable(
            route = DeepNavItem.EditDishItem.route,
            arguments = DeepNavItem.EditDishItem.arguments
        ) { backStackEntry ->
            val dishString = backStackEntry.arguments?.getString(DeepNavItem.DISH_ARGUMENT)
            val dish = Json.decodeFromString<Dish>(Uri.decode(dishString))
            DishEditScreenRoute(
                modifier = modifier,
                dish = dish,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}