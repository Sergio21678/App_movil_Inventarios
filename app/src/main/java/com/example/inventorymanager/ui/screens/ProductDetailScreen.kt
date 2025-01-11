package com.example.inventorymanager.ui.screens

<<<<<<< HEAD
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
=======
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
>>>>>>> 1fa3ed0 (Configuracion de un navbar simple de navegacion, pantalla de movimientos, opciones de agregar y retirar productos)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventorymanager.data.model.Product
<<<<<<< HEAD
import com.example.inventorymanager.ui.theme.InventoryManagerTheme

@Composable
fun ProductDetailScreen(product: Product) {
    Box(modifier = Modifier.fillMaxSize()) {
        ProductDetailBackgroundShapes() // Add the background shapes

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = product.nombre,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Código: ${product.codigo}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Descripción: ${product.descripcion}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Stock: ${product.stock}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Precio: ${product.precio} PEN",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Categoría: ${product.categoria_nombre}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Fecha de creación: ${product.fecha_creacion}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ProductDetailBackgroundShapes(modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    val secondaryColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)

    Canvas(modifier = modifier.fillMaxSize()) {
        // Dibuja un cuadrado grande en la parte inferior derecha con transparencia
        drawRect(
            color = secondaryColor,
            topLeft = Offset(x = size.width * 0.2f, y = size.height * 0.65f),
            size = androidx.compose.ui.geometry.Size(1500f, 900f)
        )

        // Dibuja un círculo grande en la parte superior izquierda con transparencia
        drawCircle(
            color = primaryColor,
            radius = 500f,
            center = Offset(x = size.width * 0.3f, y = size.height * 0.2f)
        )

        // Dibuja un círculo adicional en el centro con transparencia
        drawCircle(
            color = primaryColor,
            radius = 540f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    InventoryManagerTheme {
        ProductDetailScreen(
            product = Product(
                id = 1,
                nombre = "Producto de Ejemplo",
                precio = 100.0,
                stock = 50,
                descripcion = "Este es un producto de ejemplo.",
                categoria_nombre = "Categoría de Ejemplo",
                codigo = "P001",
                categoria = "Categoría de Ejemplo",
                fecha_creacion = "2023-10-01"
            )
        )
    }
=======
import androidx.compose.ui.Modifier
import com.example.inventorymanager.ui.viewmodel.DashboardViewModel


@Composable
fun ProductDetailScreen(product: Product, viewModel: DashboardViewModel) {
    var cantidad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.nombre,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Código: ${product.codigo}")
        Text(text = "Descripción: ${product.descripcion}")
        Text(text = "Stock: ${product.stock}")
        Text(text = "Precio: ${product.precio} PEN")
        Text(text = "Categoria: ${product.categoria_nombre}")
        Text(text = "Fecha de creación: ${product.fecha_creacion}")

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para ingresar la cantidad
        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de entrada
        Button(
            onClick = {
                viewModel.realizarMovimiento(product.id, "entrada", cantidad.toIntOrNull() ?: 0)
                mensaje = "Entrada realizada con éxito"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Entrada")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de salida
        Button(
            onClick = {
                viewModel.realizarMovimiento(product.id, "salida", cantidad.toIntOrNull() ?: 0)
                mensaje = "Salida realizada con éxito"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Salida")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {
            Text(text = mensaje, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
>>>>>>> 1fa3ed0 (Configuracion de un navbar simple de navegacion, pantalla de movimientos, opciones de agregar y retirar productos)
}