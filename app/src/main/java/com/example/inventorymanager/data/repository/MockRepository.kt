// MockRepository.kt
package com.example.inventorymanager.data.repository

import com.example.inventorymanager.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date

class MockRepository : ProductRepository {
    override fun getProducts(): Flow<List<Product>> {
        return flowOf(
            listOf(
                Product(
                    id = 1,
                    nombre = "Product 1",
                    precio = 10.0,
                    stock = 100,
                    descripcion = "Description 1",
                    categoria_nombre = "Category 1",
                    codigo = "P001",
                    categoria = "Category 1",
                    fecha_creacion = Date()
                ),
                Product(
                    id = 2,
                    nombre = "Product 2",
                    precio = 20.0,
                    stock = 200,
                    descripcion = "Description 2",
                    categoria_nombre = "Category 2",
                    codigo = "P002",
                    categoria = "Category 2",
                    fecha_creacion = Date()
                )
            )
        )
    }
}