package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inventorymanager.data.model.Categoria
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController, viewModel: DashboardViewModel, scannedCode: String) {
    // 📌 Variables de estado para capturar los datos del formulario
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // 📌 Obtener la lista de categorías desde el ViewModel
    val categorias = viewModel.categorias.collectAsState(initial = emptyList()).value
    var categoriaSeleccionada by remember { mutableStateOf<Categoria?>(null) }

    // 🚀 Llama a la función para cargar las categorías cuando se crea la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchCategorias()
    }

    // 🏗️ Estructura del formulario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // 📝 Título de la pantalla
        Text("Registrar Nuevo Producto", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        // 📝 Campo: Nombre del producto
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📝 Campo: Descripción del producto
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📝 Campo: Código del producto (prellenado y deshabilitado)
        OutlinedTextField(
            value = scannedCode,
            onValueChange = {},
            label = { Text("Código (escaneado)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📝 Campo: Stock del producto (valida si es un número entero)
        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text("Stock") },
            modifier = Modifier.fillMaxWidth(),
            isError = stock.toIntOrNull() == null
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📝 Campo: Precio del producto (valida si es un número decimal)
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth(),
            isError = precio.toDoubleOrNull() == null
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 📝 Campo: Selección de Categoría mediante Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = categoriaSeleccionada?.nombre ?: "Seleccionar Categoría",
                onValueChange = {},
                label = { Text("Categoría") },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expanded = true }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // 📌 Listado de categorías para seleccionar
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.nombre) },
                        onClick = {
                            categoriaSeleccionada = categoria
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔘 Botón para guardar el producto
        Button(
            onClick = {
                // ✅ Validación de campos antes de enviar los datos
                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && stock.toIntOrNull() != null && precio.toDoubleOrNull() != null) {
                    viewModel.addProduct(
                        nombre = nombre,
                        descripcion = descripcion,
                        codigo = scannedCode,
                        stock = stock.toInt(),
                        precio = precio.toDouble(),
                        categoriaId = categoriaSeleccionada!!.id
                    )
                    // 🔄 Navega de vuelta al Dashboard después de guardar
                    navController.navigate("dashboard") {
                        popUpTo("add_product/$scannedCode") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Producto")  // 📝 Texto del botón
        }
    }
}