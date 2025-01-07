package com.example.inventorymanager.data.model

data class Product(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val codigo: String,
    val stock: Int,
    val precio: Double,
    val categoria: String,
    val categoria_nombre: String?,
    val fecha_creacion: String
)