package com.example.inventorymanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.repository.ProductRepository
import com.example.inventorymanager.ui.screens.DashboardScreen
import com.example.inventorymanager.ui.screens.LoginScreen
import com.example.inventorymanager.ui.screens.ProductDetailScreen
import com.example.inventorymanager.ui.screens.WelcomeScreen
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
}

@Composable
fun NavGraph(navController: NavHostController, apiService: ApiService) {
    val productRepository = ProductRepository(apiService)
    val dashboardViewModel = DashboardViewModel(productRepository)

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(viewModel = dashboardViewModel, navController = navController)
        }
        composable(route = "product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            val product = dashboardViewModel.products.value?.find { it.id == productId }

            product?.let {
                ProductDetailScreen(product = it)
            }
        }
    }
}
