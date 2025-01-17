// ==============================
// Modelo del producto
// ==============================
package com.example.inventorymanager.data.model

// Representa un producto dentro del inventario
data class Product(
    val id: Int,                      // ID único del producto
    val nombre: String,               // Nombre del producto
    val descripcion: String,          // Descripción del producto
    val codigo: String,               // Código único del producto (ej. código de barras)
    val stock: Int,                   // Cantidad disponible en inventario
    val precio: Double,               // Precio del producto
    val categoria: Int,               // ID de la categoría a la que pertenece el producto
    val categoria_nombre: String?,    // Nombre de la categoría (opcional)
    val fecha_creacion: String        // Fecha de creación del producto
)
