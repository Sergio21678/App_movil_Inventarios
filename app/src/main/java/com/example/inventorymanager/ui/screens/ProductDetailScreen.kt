package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.ui.theme.InventoryManagerTheme

@Composable
fun ProductDetailScreen(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.nombre,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Código: ${product.codigo}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Descripción: ${product.descripcion}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Stock: ${product.stock}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Precio: ${product.precio} PEN",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Categoría: ${product.categoria_nombre}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Fecha de creación: ${product.fecha_creacion}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
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
}