package com.lexwilliam.hanzidict

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lexwilliam.hanzidict.ui.screen.HomeScreen
import com.lexwilliam.hanzidict.ui.screen.SearchScreen

@Composable
fun HanziDictApp() {
    val navController = rememberNavController()
    val startDestination = Screens.AppHomeScreen.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.AppHomeScreen.route) {
            HomeScreen(
                navToSearch = { navController.navigate(Screens.AppSearchScreen.route) }
            )
        }
        composable(Screens.AppSearchScreen.route) {
            SearchScreen(
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}

sealed class Screens(val route: String) {
    object AppHomeScreen: Screens("home")
    object AppSearchScreen: Screens("search")
}