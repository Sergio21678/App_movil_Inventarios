package com.example.inventorymanager.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import com.example.inventorymanager.App.Companion.context
import com.example.inventorymanager.data.remote.ApiService


@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val products by viewModel.products.observeAsState(emptyList())


    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Lista de Productos", style = MaterialTheme.typography.titleLarge)

        if (products?.isEmpty() == true) {
            Text(
                text = "No hay productos disponibles",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn {
                items(products ?: emptyList()) { product ->
                    Text(text = product.nombre ?: "Sin nombre")
                }
            }

        }

    }
}







