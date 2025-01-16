package com.example.inventorymanager.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.repository.ProductRepository
import com.example.inventorymanager.ui.screens.AddProductScreen
import com.example.inventorymanager.ui.screens.BarcodeScannerScreen
import com.example.inventorymanager.ui.screens.DashboardScreen
import com.example.inventorymanager.ui.screens.LoginScreen
import com.example.inventorymanager.ui.screens.MovimientosScreen
import com.example.inventorymanager.ui.screens.ProductDetailScreen
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel
import com.example.inventorymanager.ui.viewmodel.MovimientosViewModel
import com.example.inventorymanager.ui.viewmodel.MovimientosViewModelFactory
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Movimientos : Screen("movimientos")
    object BarcodeScanner : Screen("barcode_scanner")
    object ProductDetail : Screen("product_detail/{id}")
    object AddProduct : Screen("add_product/{codigo}")
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
        // 🟢 Pantalla de Login
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        // 🟢 Pantalla de Dashboard con botón para escanear código
        composable(Screen.Dashboard.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController) },
                floatingActionButton = {
                    IconButton(onClick = { navController.navigate(Screen.BarcodeScanner.route) }) {
                        Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Escanear Código")
                    }
                }
            ) { innerPadding ->
                DashboardScreen(
                    navController = navController,
                    viewModel = dashboardViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }

        // 🟢 Pantalla de Movimientos
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

        composable("product_detail/{codigo}") { backStackEntry ->
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            dashboardViewModel.buscarProductoPorCodigo(codigo)
            val product by dashboardViewModel.producto.observeAsState()

            if (product != null) {
                ProductDetailScreen(product = product!!, viewModel = dashboardViewModel)
            } else {
                Text(text = "Producto no encontrado")
            }
        }


        // 🟢 Pantalla de Detalle del Producto (por código escaneado)
        composable("add_product/{codigo}") { backStackEntry ->
            val scannedCode = backStackEntry.arguments?.getString("codigo")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())  // ✅ Decodificar
            } ?: ""

            AddProductScreen(navController = navController, viewModel = dashboardViewModel, scannedCode = scannedCode)
        }

        composable(Screen.ProductDetail.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("codigo")?.toIntOrNull() ?: -1
            val product = dashboardViewModel.products.value?.find { it.id == id }

            if (product != null) {
                ProductDetailScreen(product = product, viewModel = dashboardViewModel)
            } else {
                Text(text = "Producto no encontrado")
            }
        }

        // 🟢 Pantalla del Escáner de Código de Barras
        composable(Screen.BarcodeScanner.route) {
            BarcodeScannerScreen(navController, dashboardViewModel)
        }
}}