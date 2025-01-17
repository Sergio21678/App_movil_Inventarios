package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inventorymanager.ui.theme.InventoryManagerTheme
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel

// ✅ Pantalla principal del Dashboard que muestra la lista de productos
@Composable
fun DashboardScreen(
    navController: NavController,  // 🚀 Controlador de navegación
    viewModel: DashboardViewModel = viewModel(),  // 📊 ViewModel para manejar los datos
    modifier: Modifier = Modifier
) {
    val products by viewModel.products.observeAsState(emptyList())  // 📦 Productos observables
    var searchQuery by remember { mutableStateOf("") }  // 🔎 Búsqueda por nombre
    var categoryFilter by remember { mutableStateOf("") }  // 📂 Filtro por categoría
    var minPriceFilter by remember { mutableStateOf("") }  // 💵 Filtro por precio mínimo
    var maxPriceFilter by remember { mutableStateOf("") }  // 💰 Filtro por precio máximo

    // 🔍 Filtrar productos según búsqueda y filtros aplicados
    val filteredProducts = products.orEmpty().filter { product ->
        (searchQuery.isEmpty() || product.nombre.contains(searchQuery, ignoreCase = true)) &&
                (categoryFilter.isEmpty() || product.categoria_nombre?.contains(categoryFilter, ignoreCase = true) == true) &&
                (minPriceFilter.isBlank() || product.precio >= minPriceFilter.toDoubleOrNull() ?: Double.MIN_VALUE) &&
                (maxPriceFilter.isBlank() || product.precio <= maxPriceFilter.toDoubleOrNull() ?: Double.MAX_VALUE)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()  // 🔄 Cargar productos al iniciar
    }

    Box(modifier = modifier.fillMaxSize()) {
        // 🎨 Fondo decorativo
        DashboardBackgroundShapes()

        // 🏗️ Contenido principal
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            // 📝 Título
            Text(
                text = "Lista de Productos",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 20.dp)
            )

            // 🔎 Campos de búsqueda y filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar producto") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                TextField(
                    value = categoryFilter,
                    onValueChange = { categoryFilter = it },
                    label = { Text("Categoría") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = minPriceFilter,
                    onValueChange = { minPriceFilter = it },
                    label = { Text("Precio Mín") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                TextField(
                    value = maxPriceFilter,
                    onValueChange = { maxPriceFilter = it },
                    label = { Text("Precio Máx") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📋 Lista de productos filtrados
            LazyColumn {
                filteredProducts.groupBy { it.categoria_nombre }
                    .forEach { (categoriaNombre, productos) ->
                        item {
                            Text(
                                text = categoriaNombre ?: "Sin Categoría",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                        items(productos) { product ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .clickable {
                                        // 📲 Navegar a la pantalla de detalles del producto
                                        navController.navigate("product_detail/${product.codigo}")
                                    },
                                elevation = CardDefaults.cardElevation(10.dp)
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
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
}

// 🎨 Elementos decorativos del fondo del Dashboard
@Composable
fun DashboardBackgroundShapes(modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    val secondaryColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)

    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            color = secondaryColor,
            topLeft = Offset(x = size.width * 0.2f, y = size.height * 0.8f),
            size = androidx.compose.ui.geometry.Size(800f, 800f)
        )
        drawCircle(
            color = primaryColor,
            radius = 700f,
            center = Offset(x = size.width * 0.3f, y = size.height * 0.2f)
        )
        drawCircle(
            color = primaryColor,
            radius = 800f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.5f)
        )
    }
}

// 📝 Vista previa del Dashboard
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    InventoryManagerTheme {
        DashboardScreen(
            navController = rememberNavController()
        )
    }
}







