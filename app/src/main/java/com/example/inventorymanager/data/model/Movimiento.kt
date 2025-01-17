// ==============================
// Modelo del movimiento
// ==============================
package com.example.inventorymanager.data.model

// Representa un movimiento de inventario (entrada, salida o ajuste)
data class Movimiento(
    val id: Int? = null,              // ID único del movimiento (opcional)
    val tipo: String,                 // Tipo de movimiento: "entrada", "salida" o "ajuste"
    val cantidad: Int,                // Cantidad del movimiento
    val producto: Int,                // ID del producto asociado al movimiento
    val producto_nombre: String? = null, // Nombre del producto (opcional)
    val fecha: String? = null            // Fecha del movimiento (opcional)
)
