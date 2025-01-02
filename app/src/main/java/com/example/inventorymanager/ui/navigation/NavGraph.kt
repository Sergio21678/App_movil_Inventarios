package com.example.inventorymanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.repository.ProductRepository
import com.example.inventorymanager.ui.screens.DashboardScreen
import com.example.inventorymanager.ui.screens.WelcomeScreen
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Dashboard : Screen("dashboard")
}

@Composable
fun NavGraph(navController: NavHostController, apiService: ApiService) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Dashboard.route) {
            val viewModel = DashboardViewModel(ProductRepository(apiService))
            DashboardScreen(viewModel = viewModel)
        }
    }
}




