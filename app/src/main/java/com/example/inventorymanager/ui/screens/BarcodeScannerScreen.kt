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

@Composable
fun BarcodeScannerScreen(navController: NavController, viewModel: DashboardViewModel) {
    val context = LocalContext.current
    var scannedCode by remember { mutableStateOf("") }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            IntentIntegrator(context as android.app.Activity).initiateScan()
        }
    }

    // Lanzador para el escaneo
    val barcodeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            intentResult?.contents?.let { code ->
                scannedCode = code
                viewModel.buscarProductoYRedirigir(navController, scannedCode)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val integrator = IntentIntegrator(context as Activity)
            integrator.setPrompt("Escanea el código de barras")
            integrator.setBeepEnabled(true)
            integrator.setOrientationLocked(false)
            barcodeLauncher.launch(integrator.createScanIntent())
        }) {
            Text("Escanear Código")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (scannedCode.isNotEmpty()) {
            Text("Código Escaneado: $scannedCode")
        }
    }
}
