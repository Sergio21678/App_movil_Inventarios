package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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

@Composable
<<<<<<< HEAD
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = viewModel()) {
    val products by viewModel.products.observeAsState(emptyList())
=======
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel,
    modifier: Modifier = Modifier // Declarar el parámetro `modifier`
) {
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
>>>>>>> 1fa3ed0 (Configuracion de un navbar simple de navegacion, pantalla de movimientos, opciones de agregar y retirar productos)

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

<<<<<<< HEAD
    Box(modifier = Modifier.fillMaxSize()) {
        DashboardBackgroundShapes() // Add the background shapes
=======
    Column(
        modifier = modifier // Utilizar el `modifier`
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
>>>>>>> 1fa3ed0 (Configuracion de un navbar simple de navegacion, pantalla de movimientos, opciones de agregar y retirar productos)

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

<<<<<<< HEAD
            LazyColumn {
                products?.groupBy { it.categoria_nombre }?.forEach { (categoriaNombre, productos) ->
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
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = product.nombre,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    )
=======
        LazyColumn {
            filteredProducts.groupBy { it.categoria_nombre }.forEach { (categoriaNombre, productos) ->
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
>>>>>>> 1fa3ed0 (Configuracion de un navbar simple de navegacion, pantalla de movimientos, opciones de agregar y retirar productos)
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

<<<<<<< HEAD
@Composable
fun DashboardBackgroundShapes(modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    val secondaryColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)

    Canvas(modifier = modifier.fillMaxSize()) {
        // Dibuja un cuadrado grande en la parte inferior derecha con transparencia
        drawRect(
            color = secondaryColor,
            topLeft = Offset(x = size.width * 0.2f, y = size.height * 0.8f),
            size = androidx.compose.ui.geometry.Size(800f, 800f)
        )

        // Dibuja un círculo grande en la parte superior izquierda con transparencia
        drawCircle(
            color = primaryColor,
            radius = 700f,
            center = Offset(x = size.width * 0.3f, y = size.height * 0.2f)
        )

        // Dibuja un círculo adicional en el centro con transparencia
        drawCircle(
            color = primaryColor,
            radius = 800f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    InventoryManagerTheme {
        DashboardScreen(
            navController = rememberNavController(),
            viewModel = DashboardViewModel()
        )
    }
}
=======

>>>>>>> 1fa3ed0 (Configuracion de un navbar simple de navegacion, pantalla de movimientos, opciones de agregar y retirar productos)
