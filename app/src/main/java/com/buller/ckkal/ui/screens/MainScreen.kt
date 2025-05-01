package com.buller.ckkal.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.buller.ckkal.R
import com.buller.ckkal.domain.entities.Dish
import com.buller.ckkal.ui.screens.home.AddIngredientRoute
import com.buller.ckkal.ui.screens.home.AllWeightRoute
import com.buller.ckkal.ui.screens.home.CalculationResultRoute
import com.buller.ckkal.ui.screens.home.HomeRoute
import com.buller.ckkal.ui.screens.info.ProfileScreen
import com.buller.ckkal.ui.screens.saved.DishEditScreenRoute
import com.buller.ckkal.ui.screens.saved.SavedDishesRoute
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val modalNavController = rememberNavController()
    val bottomBarHeight = remember { mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            when (currentRoute) {
                DialogNavItem.AddIngredientItem.route -> {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "New ingredient",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = stringResource(R.string.cancel)
                                )
                            }
                        })
                }

                DialogNavItem.AllWeightItem.route -> {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Total weight",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = stringResource(R.string.cancel)
                                )
                            }
                        })
                }

                else -> {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Your ingredients",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        navigationIcon = {})
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        val heightPx = coordinates.size.height
                        bottomBarHeight.value = with(localDensity) {
                            heightPx.toDp()
                        }
                    }
            ) {
                BottomNavigationBar(navController = navController)
            }
        },
    )
    { innerPadding ->
        MainGraph(
            navController = navController,
            modalNavController = modalNavController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            bottomBarHeight = bottomBarHeight.value
        )
    }
    ModalGraph(modalNavController = modalNavController)
}

@Composable
fun MainGraph(
    navController: NavHostController,
    modalNavController: NavHostController,
    modifier: Modifier = Modifier,
    bottomBarHeight: Dp
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavItem.HomeItem.route,
        route = "main_graph"
    ) {
        composable(route = BottomNavItem.HomeItem.route) {
            HomeRoute(
                sharedViewModel = sharedViewModel,
                onAddIngredient = {
                    navController.navigate(route = DialogNavItem.ADD_INGREDIENT_ROUTE) {
                        restoreState = true
                    }
                },
                onAddTotalWeight = {
                    navController.navigate(route = DialogNavItem.AllWeightItem.route)
                })
        }
        composable(route = BottomNavItem.SavedDishesItem.route) {
            SavedDishesRoute(
                modifier = modifier,
                sharedViewModel = sharedViewModel,
                navigateToEditDishScreen = { dish ->
                    modalNavController.navigate(DeepNavItem.EditDishItem.createRoute(dish = dish))
                },
                navigateToHomeScreen = {
                    navController.navigate(route = BottomNavItem.HomeItem.route) {
                        popUpTo(BottomNavItem.SavedDishesItem.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
        composable(route = BottomNavItem.InfoItem.route) {
            ProfileScreen()
        }
        composable(route = DialogNavItem.AddIngredientItem.route) {
            AddIngredientRoute(
                viewModel = sharedViewModel,
                onAddTotalWeight = {
                    navController.navigate(route = DialogNavItem.AllWeightItem.route) {
                        popUpTo(DialogNavItem.AddIngredientItem.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(route = DialogNavItem.AllWeightItem.route) {
            AllWeightRoute(
                modifier = modifier,
                viewModel = sharedViewModel,
                bottomBarHeight = bottomBarHeight,
                onBack = {
                    navController.popBackStack()
                },
                onCalculateScreen = {
                    navController.navigate(route = DialogNavItem.CalculateItem.route) {
                        launchSingleTop = true
                    }
                })
        }
        composable(route = DialogNavItem.CalculateItem.route) {
            CalculationResultRoute(
                viewModel = sharedViewModel,
                navigateToHomeScreen = {
                    navController.navigate(route = BottomNavItem.HOME) {
                        launchSingleTop = true
                    }
                },
                navigateToSavedDishes = {
                    navController.navigate(route = BottomNavItem.SavedDishesItem.route) {
                        popUpTo(BottomNavItem.HomeItem.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
fun ModalGraph(modalNavController: NavHostController) {
    NavHost(navController = modalNavController, startDestination = "empty", route = "modal_graph") {
        composable("empty") { }
        composable(
            route = DeepNavItem.EditDishItem.route,
            arguments = DeepNavItem.EditDishItem.arguments
        ) { backStackEntry ->
            val dishString = backStackEntry.arguments?.getString(DeepNavItem.DISH_ARGUMENT)
            val dish = Json.decodeFromString<Dish>(Uri.decode(dishString))
            DishEditScreenRoute(
                dish = dish,
                onBack = { modalNavController.popBackStack() },
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}