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

@Composable
fun BottomNavBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            selected = currentRoute == Screen.Dashboard.route,
            onClick = {
                if (currentRoute != Screen.Dashboard.route) {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Movimientos") },
            label = { Text("Movimientos") },
            selected = currentRoute == Screen.Movimientos.route,
            onClick = {
                if (currentRoute != Screen.Movimientos.route) {
                    navController.navigate(Screen.Movimientos.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = false }
                    }
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(Icons.Default.CameraAlt, contentDescription = "Escanear")
            },
            label = { Text("Escanear") },
            selected = false,
            onClick = {
                navController.navigate(Screen.BarcodeScanner.route)
            }
        )
    }
}