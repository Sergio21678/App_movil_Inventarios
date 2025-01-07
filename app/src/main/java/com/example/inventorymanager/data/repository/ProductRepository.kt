package com.example.inventorymanager.data.repository

import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.model.TokenResponse
import com.example.inventorymanager.data.remote.ApiService
import retrofit2.Response

class ProductRepository(private val apiService: ApiService) {

    // Obtener todos los productos
    suspend fun getProducts(): Response<List<Product>> {
        return apiService.getProducts() // Asegúrate de que este método esté definido en tu ApiService
    }

    // Agregar un producto
    suspend fun addProduct(product: Product): Response<Product> {
        return apiService.addProduct(product)
    }

    // Eliminar un producto por ID
    suspend fun deleteProduct(productId: Int): Response<Unit> {
        return apiService.deleteProduct(productId)
    }

    // Metodo de Busqueda
    suspend fun searchProducts(
        nombre: String? = null,
        categoria: String? = null,
        precioMin: Double? = null,
        precioMax: Double? = null
    ): Response<List<Product>> {
        return apiService.searchProducts(nombre, categoria, precioMin, precioMax)
    }

    // Login
    suspend fun login(credentials: Map<String, String>) = apiService.login(credentials)
}
