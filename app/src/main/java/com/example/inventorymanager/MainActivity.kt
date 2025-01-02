package com.example.inventorymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventorymanager.ui.screens.DashboardScreen
import com.example.inventorymanager.ui.screens.LoginScreen
import com.example.inventorymanager.ui.theme.InventoryManagerTheme
import java.time.format.TextStyle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.setContent
import com.example.inventorymanager.data.remote.ApiClient
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.repository.ProductRepository
import com.example.inventorymanager.ui.navigation.NavGraph
import com.example.inventorymanager.ui.screens.WelcomeScreen
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiClient.getRetrofit(this).create(ApiService::class.java)
        val repository = ProductRepository(apiService)
        val viewModel = DashboardViewModel(repository)

        setContent {
            InventoryManagerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, apiService = apiService)
            }
        }
    }
}





@Composable
fun AppTheme(content: @Composable () -> Unit, apiService: ApiService) {
    InventoryManagerTheme {
    }
}

@Composable
fun AppNavigation(apiService: ApiService) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
    }
}

