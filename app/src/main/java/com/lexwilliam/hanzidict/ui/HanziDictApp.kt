package com.lexwilliam.hanzidict.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lexwilliam.hanzidict.ui.screen.home.HomeScreen
import com.lexwilliam.hanzidict.ui.screen.search.SearchScreen
import com.lexwilliam.hanzidict.ui.screen.search.SearchViewModel

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
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                viewModel = searchViewModel,
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}

sealed class Screens(val route: String) {
    object AppHomeScreen: Screens("home")
    object AppSearchScreen: Screens("search")
}