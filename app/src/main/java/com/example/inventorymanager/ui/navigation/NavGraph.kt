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

// 📌 Definición de las rutas de navegación
sealed class Screen(val route: String) {
    object Login : Screen("login")                           // Ruta para la pantalla de login
    object Dashboard : Screen("dashboard")                   // Ruta para la pantalla principal (dashboard)
    object Movimientos : Screen("movimientos")               // Ruta para la pantalla de movimientos
    object BarcodeScanner : Screen("barcode_scanner")        // Ruta para la pantalla de escaneo de código
    object ProductDetail : Screen("product_detail/{id}")     // Ruta para detalle de producto
    object AddProduct : Screen("add_product/{codigo}")       // Ruta para agregar un nuevo producto
}

// 🚀 Gráfico de navegación
@Composable
fun NavGraph(navController: NavHostController, apiService: ApiService) {
    val productRepository = ProductRepository(apiService)  // Repositorio para gestionar datos
    val dashboardViewModel = DashboardViewModel(productRepository)  // ViewModel del dashboard
    val movimientosViewModel: MovimientosViewModel = viewModel(
        factory = MovimientosViewModelFactory(productRepository)
    )

    // 🌐 Configuración de navegación
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route  // Establece la pantalla de inicio
    ) {
        // 🟢 Pantalla de Login
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        // 🟢 Pantalla de Dashboard con botón flotante para escanear código
        composable(Screen.Dashboard.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController) },  // Barra de navegación inferior
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

        // 🟢 Pantalla de Detalle del Producto (por código escaneado)
        composable("product_detail/{codigo}") { backStackEntry ->
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            dashboardViewModel.buscarProductoPorCodigo(codigo)  // Buscar producto
            val product by dashboardViewModel.producto.observeAsState()

            if (product != null) {
                ProductDetailScreen(product = product!!, viewModel = dashboardViewModel)
            } else {
                Text(text = "Producto no encontrado")
            }
        }

        // 🟢 Pantalla para agregar un nuevo producto después de escanear
        composable("add_product/{codigo}") { backStackEntry ->
            val scannedCode = backStackEntry.arguments?.getString("codigo")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())  // ✅ Decodificar el código escaneado
            } ?: ""

            AddProductScreen(navController = navController, viewModel = dashboardViewModel, scannedCode = scannedCode)
        }

        // 🟢 Pantalla de Detalle del Producto
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
    }
}