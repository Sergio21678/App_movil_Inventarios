package com.example.inventorymanager.ui.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel
import com.google.zxing.integration.android.IntentIntegrator

// ✅ Pantalla para escanear códigos de barras
@Composable
fun BarcodeScannerScreen(navController: NavController, viewModel: DashboardViewModel) {
    val context = LocalContext.current  // 📱 Contexto actual de la aplicación
    var scannedCode by remember { mutableStateOf("") }  // 🔍 Almacena el código escaneado
    var isScanning by remember { mutableStateOf(false) }  // 🔄 Controla si se está escaneando

    // 📸 Lanzador para pedir permiso de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // ✅ Si se concede el permiso, inicia el escaneo
            IntentIntegrator(context as android.app.Activity).initiateScan()
        }
    }

    // 📲 Lanzador para capturar el resultado del escaneo
    val barcodeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            intentResult?.contents?.let { scannedCode ->
                // 🔎 Busca el producto por el código escaneado y redirige
                viewModel.buscarProductoYRedirigir(navController, scannedCode)
            }
        }
    }

    // 🏗️ UI de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 🔘 Botón para iniciar el escaneo
        Button(onClick = {
            if (!isScanning) {
                isScanning = true
                val integrator = IntentIntegrator(context as Activity).apply {
                    setPrompt("Escanea el código de barras")  // 📝 Mensaje al usuario
                    setBeepEnabled(true)                      // 🔔 Sonido al escanear
                    setOrientationLocked(false)               // 🔄 Permite rotar la pantalla
                }
                barcodeLauncher.launch(integrator.createScanIntent())  // 🚀 Inicia el escaneo
            }
        }) {
            Text("Escanear Código")  // 📝 Texto del botón
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📋 Muestra el código escaneado si existe
        if (scannedCode.isNotEmpty()) {
            Text("Código Escaneado: $scannedCode")
        }
    }
}
