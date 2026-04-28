package com.buller.ckkal.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.forEach
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.buller.ckkal.ui.screens.navigation.MainNavGraph
import com.buller.ckkal.ui.screens.views.BottomNavItem
import com.buller.ckkal.ui.screens.views.BottomNavigationBar
import com.buller.ckkal.ui.screens.views.DialogNavItem
import java.util.Map
import java.util.Map.entry

const val TAG = "NavGraphDebug"

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val bottomBarHeight = remember { mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current
    val sharedViewModel: SharedViewModel = hiltViewModel()

    val bottomNavItemSaver = Saver<BottomNavItem, String>(
        save = { it.route },
        restore = { route ->
            when (route) {
                BottomNavItem.HomeItem.route -> BottomNavItem.HomeItem
                BottomNavItem.SavedDishesItem.route -> BottomNavItem.SavedDishesItem
                BottomNavItem.InfoItem.route -> BottomNavItem.InfoItem
                else -> BottomNavItem.HomeItem
            }
        }
    )

    var currentTab by rememberSaveable(stateSaver = bottomNavItemSaver) {
        mutableStateOf<BottomNavItem>(
            BottomNavItem.HomeItem
        )
    }

    LaunchedEffect(backStackEntry.value?.destination?.route) {
        val route = backStackEntry.value?.destination?.route
        currentTab = when {
            route == BottomNavItem.HomeItem.route -> BottomNavItem.HomeItem
            route == BottomNavItem.SavedDishesItem.route -> BottomNavItem.SavedDishesItem
            route == BottomNavItem.InfoItem.route -> BottomNavItem.InfoItem
            else -> currentTab // не трогаем, если не bottom tab
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                navController = navController,
                currentRoute = currentRoute,
                onSearch = {},
                navigateToAddIngredientScreen = {
                    navController.navigate(DialogNavItem.AddIngredientItem.createRoute(isEditing = true))
                })
        },
        bottomBar = {
            Box(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    val heightPx = coordinates.size.height
                    bottomBarHeight.value = with(localDensity) {
                        heightPx.toDp()
                    }
                }) {
                BottomNavigationBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        currentTab = tab
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        },
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            sharedViewModel = sharedViewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            bottomBarHeight = bottomBarHeight.value
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}