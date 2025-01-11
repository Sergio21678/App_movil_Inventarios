package com.example.inventorymanager.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.repository.ProductRepository
import com.example.inventorymanager.ui.screens.DashboardScreen
import com.example.inventorymanager.ui.screens.LoginScreen
import com.example.inventorymanager.ui.screens.MovimientosScreen
import com.example.inventorymanager.ui.screens.ProductDetailScreen
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel
import com.example.inventorymanager.ui.viewmodel.MovimientosViewModel
import com.example.inventorymanager.ui.viewmodel.MovimientosViewModelFactory

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Movimientos : Screen("movimientos")
}

@Composable
fun NavGraph(navController: NavHostController, apiService: ApiService) {
    val productRepository = ProductRepository(apiService)
    val dashboardViewModel = DashboardViewModel(productRepository)
    val movimientosViewModel: MovimientosViewModel = viewModel(
        factory = MovimientosViewModelFactory(productRepository)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Dashboard.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController) }
            ) { innerPadding ->
                DashboardScreen(
                    navController = navController,
                    viewModel = dashboardViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        composable(Screen.Movimientos.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController) }
            ) { innerPadding ->
                MovimientosScreen(
                    navController = navController,
                    viewModel = movimientosViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        composable(
            route = "product_detail/{productId}"
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            val product = dashboardViewModel.products.value?.find { it.id == productId }

            if (product != null) {
                ProductDetailScreen(product = product, viewModel = dashboardViewModel)
            } else {
                // Manejo de errores: Si el producto no existe
                Text(text = "Producto no encontrado")
            }
        }


    }
}
