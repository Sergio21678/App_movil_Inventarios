// ==============================
// Modelo de la categoría
// ==============================
package com.example.inventorymanager.data.model

// Representa la categoría de un producto en el sistema
data class Categoria(
    val id: Int,                 // ID único de la categoría
    val nombre: String,          // Nombre de la categoría
    val descripcion: String?     // Descripción opcional de la categoría
)
