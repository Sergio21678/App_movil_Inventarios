package com.example.inventorymanager.ui.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List

// Función composable que define la barra de navegación inferior
@Composable
fun BottomNavBar(navController: NavController) {
    // Obtiene la ruta actual de la navegación para saber qué ítem está seleccionado
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    // Crea la barra de navegación
    NavigationBar {
        // Ítem de navegación para el Dashboard
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },  // Ícono de inicio
            label = { Text("Dashboard") },  // Texto debajo del ícono
            selected = currentRoute == Screen.Dashboard.route,  // Comprueba si está seleccionado
            onClick = {
                // Navega al Dashboard solo si no está actualmente en él
                if (currentRoute != Screen.Dashboard.route) {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }  // Limpia el historial hasta el Dashboard
                    }
                }
            }
        )

        // Ítem de navegación para Movimientos
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Movimientos") },  // Ícono de lista
            label = { Text("Movimientos") },  // Texto debajo del ícono
            selected = currentRoute == Screen.Movimientos.route,  // Comprueba si está seleccionado
            onClick = {
                // Navega a Movimientos solo si no está actualmente en él
                if (currentRoute != Screen.Movimientos.route) {
                    navController.navigate(Screen.Movimientos.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = false }  // No elimina el Dashboard del historial
                    }
                }
            }
        )

        // Ítem de navegación para el escáner de código de barras
        NavigationBarItem(
            icon = {
                Icon(Icons.Default.CameraAlt, contentDescription = "Escanear")  // Ícono de cámara
            },
            label = { Text("Escanear") },  // Texto debajo del ícono
            selected = false,  // Nunca está seleccionado (acción puntual)
            onClick = {
                // Navega al escáner de código de barras
                navController.navigate(Screen.BarcodeScanner.route)
            }
        )
    }
}