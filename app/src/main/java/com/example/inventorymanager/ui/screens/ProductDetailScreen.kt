package com.example.inventorymanager.ui.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel


@Composable
fun ProductDetailScreen(product: Product?, viewModel: DashboardViewModel) {
    var cantidad by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf("") }

    // Verificar si el producto es nulo
    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Producto no encontrado", style = MaterialTheme.typography.titleLarge)
        }
        return
    }

    // Fondo y contenido superpuesto
    Box(modifier = Modifier.fillMaxSize()) {
        ProductDetailBackgroundShapes() // Fondo decorativo

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Información del Producto
            Text(
                text = product.nombre,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            InfoCard("Código: ${product.codigo}")
            InfoCard("Descripción: ${product.descripcion}")
            InfoCard("Stock: ${product.stock}")
            InfoCard("Precio: ${product.precio} PEN")
            InfoCard("Categoría: ${product.categoria_nombre}")
            InfoCard("Fecha de creación: ${product.fecha_creacion}")

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para ingresar cantidad
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(),
                isError = cantidad.toIntOrNull() == null
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para registrar entrada
            Button(
                onClick = {
                    if (cantidad.isNotEmpty() && cantidad.toIntOrNull() != null) {
                        viewModel.realizarMovimiento(product.id, "entrada", cantidad.toInt())
                        mensaje = "Entrada registrada exitosamente"
                        cantidad = ""
                    } else {
                        mensaje = "Ingrese una cantidad válida"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Entrada")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para registrar salida
            Button(
                onClick = {
                    if (cantidad.isNotEmpty() && cantidad.toIntOrNull() != null) {
                        viewModel.realizarMovimiento(product.id, "salida", cantidad.toInt())
                        mensaje = "Salida registrada exitosamente"
                        cantidad = ""
                    } else {
                        mensaje = "Ingrese una cantidad válida"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Registrar Salida")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de confirmación
            if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// ✅ Composable para mostrar información en Cards
@Composable
fun InfoCard(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// ✅ Composable para el fondo decorativo
@Composable
fun ProductDetailBackgroundShapes(modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    val secondaryColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)

    Canvas(modifier = modifier.fillMaxSize()) {
        // Fondo rectangular
        drawRect(
            color = secondaryColor,
            topLeft = Offset(x = size.width * 0.2f, y = size.height * 0.65f),
            size = androidx.compose.ui.geometry.Size(1500f, 900f)
        )

        // Círculo superior izquierdo
        drawCircle(
            color = primaryColor,
            radius = 500f,
            center = Offset(x = size.width * 0.3f, y = size.height * 0.2f)
        )

        // Círculo central
        drawCircle(
            color = primaryColor,
            radius = 540f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.5f)
        )
    }
}