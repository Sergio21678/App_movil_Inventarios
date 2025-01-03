package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.inventorymanager.data.model.Product
import androidx.compose.ui.Modifier


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
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Código: ${product.codigo}")
        Text(text = "Descripción: ${product.descripcion}")
        Text(text = "Stock: ${product.stock}")
        Text(text = "Precio: ${product.precio} PEN")
        Text(text = "Categoria: ${product.categoria_nombre}")
        Text(text = "Fecha de creación: ${product.fecha_creacion}")
    }
}
