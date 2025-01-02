package com.example.inventorymanager.data.model

data class Product(
    val nombre: String,
    val descripcion: String,
    val codigo: String,
    val stock: Int,
    val precio: Double,
    val categoria: String?,
    val fecha_creacion: String
)