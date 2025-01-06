package com.example.inventorymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.inventorymanager.data.remote.ApiClient
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.ui.navigation.NavGraph
import com.example.inventorymanager.ui.theme.InventoryManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiClient.getRetrofit(this).create(ApiService::class.java)

        setContent {
            InventoryManagerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, apiService = apiService)
            }
        }
    }
}