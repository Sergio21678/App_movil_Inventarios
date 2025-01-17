package com.example.inventorymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.inventorymanager.data.remote.ApiClient
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.ui.navigation.NavGraph
import com.example.inventorymanager.ui.theme.InventoryManagerTheme

/**
 * 🚀 Actividad principal de la aplicación.
 *
 * Esta clase es el punto de entrada de la aplicación y se encarga de inicializar
 * la interfaz de usuario utilizando Jetpack Compose y configurar la navegación.
 */
class MainActivity : ComponentActivity() {

    /**
     * 🔄 Método llamado cuando la actividad se crea.
     *
     * @param savedInstanceState Estado de la instancia si la actividad se recrea.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🌐 Inicializa el servicio de API usando ApiClient
        val apiService = ApiClient.getRetrofit(this).create(ApiService::class.java)

        // 🖌️ Establece el contenido de la pantalla usando Jetpack Compose
        setContent {
            // 🎨 Aplica el tema personalizado de la aplicación
            InventoryManagerTheme {
                // 📦 Controlador de navegación para gestionar las pantallas
                val navController = rememberNavController()

                // 🗺️ Configura el grafo de navegación de la app
                NavGraph(navController = navController, apiService = apiService)
            }
        }
    }
}
