package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel) {
    val products by viewModel.products.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var categoryFilter by remember { mutableStateOf("") }
    var minPriceFilter by remember { mutableStateOf("") }
    var maxPriceFilter by remember { mutableStateOf("") }


    // Filtrar productos basados en búsqueda y filtros
    val filteredProducts = products.orEmpty().filter { product ->
        (searchQuery.isEmpty() || product.nombre.contains(searchQuery, ignoreCase = true)) &&
                (categoryFilter.isEmpty() || product.categoria_nombre?.contains(categoryFilter, ignoreCase = true) == true) &&
                (minPriceFilter.isBlank() || product.precio >= minPriceFilter.toDoubleOrNull() ?: Double.MIN_VALUE) &&
                (maxPriceFilter.isBlank() || product.precio <= maxPriceFilter.toDoubleOrNull() ?: Double.MAX_VALUE)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Productos",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Campos de búsqueda avanzada
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar por nombre") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = categoryFilter,
            onValueChange = { categoryFilter = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = minPriceFilter,
            onValueChange = { minPriceFilter = it },
            label = { Text("Precio mínimo") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = maxPriceFilter,
            onValueChange = { maxPriceFilter = it },
            label = { Text("Precio máximo") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        LazyColumn {
            filteredProducts?.groupBy { it.categoria_nombre }?.forEach { (categoriaNombre, productos) ->
                item {
                    Text(
                        text = categoriaNombre ?: "Sin Categoría",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(productos) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                // Navega a la pantalla de detalles del producto
                                navController.navigate("product_detail/${product.id}")
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = product.nombre,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Precio: ${product.precio} | Stock: ${product.stock}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Descripción: ${product.descripcion}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

