package com.example.inventorymanager.data.model

data class Movimiento(
    val id: Int? = null, // Opcional
    val tipo: String,    // Obligatorio
    val cantidad: Int,   // Obligatorio
    val producto: Int,   // Obligatorio
    val producto_nombre: String? = null, // Opcional
    val fecha: String? = null // Opcional
)
