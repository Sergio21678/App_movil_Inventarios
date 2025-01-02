package com.example.inventorymanager.data.repository

import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.model.TokenResponse
import com.example.inventorymanager.data.remote.ApiService
import retrofit2.Response

class ProductRepository(private val apiService: ApiService) {

    // Obtener todos los productos
    class ProductRepository(private val apiService: ApiService) {
        suspend fun getProducts(): List<Product>? {
            val response = apiService.getProducts()
            return if (response.isSuccessful) {
                response.body()
            } else {
                null // Maneja errores aquí si es necesario
            }
        }
    }


    // Agregar un producto
    suspend fun addProduct(product: Product): Response<Product> {
        return apiService.addProduct(product)
    }

    // Eliminar un producto por ID
    suspend fun deleteProduct(productId: Int): Response<Unit> {
        return apiService.deleteProduct(productId)
    }

    // Login
    suspend fun login(credentials: Map<String, String>): Response<TokenResponse> {
        return apiService.login(credentials)
    }

    suspend fun getProducts(): Response<List<Product>> {
        return apiService.getProducts() // Asegúrate de que este método esté definido en tu ApiService
    }
}
