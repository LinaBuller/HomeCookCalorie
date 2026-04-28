package com.buller.ckkal.ui.screens.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.buller.ckkal.ui.screens.views.BottomNavItem
import com.buller.ckkal.ui.screens.views.DialogNavItem
import com.buller.ckkal.ui.screens.SharedViewModel
import com.buller.ckkal.ui.screens.TAG
import com.buller.ckkal.ui.screens.home.AddIngredientRoute
import com.buller.ckkal.ui.screens.home.AllWeightRoute
import com.buller.ckkal.ui.screens.home.CalculationResultRoute
import com.buller.ckkal.ui.screens.home.HomeRoute
import com.buller.ckkal.ui.screens.info.ProfileScreen
import com.buller.ckkal.ui.screens.saved.DishEditScreenRoute
import com.buller.ckkal.ui.screens.saved.DishEditViewModel
import com.buller.ckkal.ui.screens.saved.SavedDishesRoute


const val MAIN_NAV_GRAPH_ROUTE = "main_graph"
const val BOTTOM_NAV_GRAPH_ROUTE = "bottom_nav_graph"
const val ADD_DISH_NAV_GRAPH_ROUTE = "add_dish_graph"

@Composable
fun MainNavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier,
    bottomBarHeight: Dp
) {
    val dishEditViewModel: DishEditViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BOTTOM_NAV_GRAPH_ROUTE,
        route = MAIN_NAV_GRAPH_ROUTE
    ) {
        Log.d(TAG, "Building Main Nav Graph")
        bottomNavGraph(
            navController = navController,
            viewModel = sharedViewModel,
            dishEditViewModel = dishEditViewModel
        )
        addDishNavGraph(
            navController = navController,
            viewModel = sharedViewModel,
            dishEditViewModel = dishEditViewModel,
            bottomBarHeight = bottomBarHeight
        ) { route ->
            Log.d(TAG, "Navigating to graph: $route from CalculationResultRoute")
            navController.navigate(route = route) {
                popUpTo(MAIN_NAV_GRAPH_ROUTE) {
                    inclusive = false
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    viewModel: SharedViewModel,
    dishEditViewModel: DishEditViewModel,
) {
    Log.d(TAG, "Building Bottom Nav Graph")
    navigation(startDestination = BottomNavItem.HomeItem.route, route = BOTTOM_NAV_GRAPH_ROUTE) {
        composable(route = BottomNavItem.HomeItem.route) {
            HomeRoute(
                sharedViewModel = viewModel,
                navigateToAddIngredientScreen = {
                    Log.d(TAG, "Navigating from Home to AddIngredientScreen")
                    navController.navigate(DialogNavItem.AddIngredientItem.createRoute(isEditing = false)) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToAddTotalWeight = {
                    Log.d(TAG, "Navigating from Home to TotalWeight (standalone)")
                    navController.navigate(DialogNavItem.TotalWeightItem.createRoute())

                })
        }
        composable(route = BottomNavItem.SavedDishesItem.route) { entry ->
            Log.d(TAG, "Entering Composable: SavedDishesRoute")
            SavedDishesRoute(
                modifier = Modifier,
                sharedViewModel = viewModel,
                navigateToEditDishScreen = { dish ->
                    Log.d(TAG, "Navigating from SavedDishes to EditDishScreen")
                    dishEditViewModel.initDish(dish)
                    navController.navigate(DialogNavItem.EditDishItem.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToHomeScreen = {
                    Log.d(TAG, "Navigating from SavedDishes to HomeScreen")
                    navController.navigate(route = BottomNavItem.HomeItem.route) {
                        restoreState = true
                    }
                })
        }

        composable(route = BottomNavItem.InfoItem.route) {
            Log.d(TAG, "Entering Composable: ProfileScreen")
            ProfileScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.addDishNavGraph(
    navController: NavHostController,
    viewModel: SharedViewModel,
    dishEditViewModel: DishEditViewModel,
    bottomBarHeight: Dp,
    onNavigateToGraph: (String) -> Unit
) {
    Log.d(TAG, "Building Add Dish Nav Graph")
    navigation(
        startDestination = DialogNavItem.AddIngredientItem.route,
        route = ADD_DISH_NAV_GRAPH_ROUTE
    ) {
        composable(
            route = DialogNavItem.AddIngredientItem.route,
            arguments = DialogNavItem.AddIngredientItem.arguments
        ) { entry ->
            Log.d(TAG, "Entering Composable: AddIngredientRoute")
            val isEditing =
                entry.arguments?.getBoolean(DialogNavItem.IS_EDITING_INGREDIENT_ARGUMENT) == true
            AddIngredientRoute(
                isEditing = isEditing,
                viewModel = viewModel,
                bottomBarHeight = bottomBarHeight,
                navigateToAddTotalWeightScreen = {
                    Log.d(TAG, "Navigating from AddIngredient to TotalWeight")
                    navController
                        .navigate(DialogNavItem.TotalWeightItem.createRoute()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                },
                onUpdateDish = {
                    Log.d(TAG, "Ingredient Updated, popping back stack from AddIngredient")
                    dishEditViewModel.setIngredient(it)
                    navController.popBackStack()
                },
                onBack = {
                    Log.d(TAG, "Popping back stack from AddIngredient")
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = DialogNavItem.TotalWeightItem.route,
            arguments = DialogNavItem.TotalWeightItem.arguments
        ) { entry ->
            Log.d(TAG, "Entering Composable: AllWeightRoute")
            val oldWeightString = entry.arguments?.getString(DialogNavItem.OLD_WEIGHT_ARGUMENT)
            val oldWeight = oldWeightString?.toDoubleOrNull()
            AllWeightRoute(
                oldWeight = oldWeight,
                viewModel = viewModel,
                bottomBarHeight = bottomBarHeight,
                onBack = {
                    Log.d(TAG, "Popping back stack from TotalWeight")
                    navController.popBackStack()
                },
                navigateCalculateScreen = {
                    Log.d(TAG, "Navigating from TotalWeight to CalculateScreen")
                    navController.navigate(route = DialogNavItem.CalculateItem.route) {
                        launchSingleTop = true
                    }
                },
                onUpdateDish = { newWeight ->
                    Log.d(TAG, "Weight Updated, popping back stack from TotalWeight")
                    dishEditViewModel.setWeight(newWeight)
                    navController.popBackStack()
                })
        }

        composable(route = DialogNavItem.CalculateItem.route) {
            Log.d(TAG, "Entering Composable: CalculationResultRoute")
            CalculationResultRoute(
                viewModel = viewModel,
                bottomBarHeight = bottomBarHeight,
                navigateToHomeScreen = {
                    Log.d(TAG, "Navigating from CalculateScreen to HomeScreen (via onNavigateToGraph)")
                    onNavigateToGraph(BottomNavItem.HomeItem.route)
                },
                navigateToSavedDishes = {
                    Log.d(TAG, "Navigating from CalculateScreen to SavedDishes (via onNavigateToGraph)")
                    onNavigateToGraph(BottomNavItem.SavedDishesItem.route)
                },
            )
        }
        composable(
            route = DialogNavItem.EditDishItem.route,
            arguments = DialogNavItem.EditDishItem.arguments
        ) { entry ->
            Log.d(TAG, "Entering Composable: DishEditScreenRoute")
            DishEditScreenRoute(
                dishEditViewModel = dishEditViewModel,
                navigateToAddTotalWeightScreen = { oldWeight ->
                    Log.d(TAG, "Navigating from EditDishScreen to TotalWeight")
                    navController.navigate(
                        DialogNavItem.TotalWeightItem.createRoute(oldWeight = oldWeight)
                    )
                },
                onBack = {
                    Log.d(TAG, "Popping back stack from DishEditScreenRoute")
                    navController.popBackStack()
                }
            )
        }
    }
}
