package com.example.inventorymanager.data.repository

import com.example.inventorymanager.data.model.Categoria
import com.example.inventorymanager.data.model.Movimiento
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

    suspend fun getMovimientos(): Response<List<Movimiento>> {
        return apiService.getMovimientos()
    }

    suspend fun getProductoNombre(productoId: Int): String? {
        return try {
            val response = apiService.getProductById(productoId)
            if (response.isSuccessful) {
                response.body()?.nombre
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error al obtener el nombre del producto: ${e.message}")
            null
        }
    }

    suspend fun createMovimiento(movimiento: Movimiento): Response<Movimiento> {
        return apiService.createMovimiento(movimiento)
    }

    suspend fun registrarMovimiento(productoId: Int, tipo: String, cantidad: Int): Response<Unit> {
        return apiService.registrarMovimiento(productoId, tipo, cantidad)
    }

    suspend fun addMovimiento(productId: Int, tipo: String, cantidad: Int): Response<Unit> {
        return apiService.addMovimiento(productId, tipo, cantidad)
    }

    // Buscar movimientos por tipo, nombre de producto o fecha
    suspend fun searchMovimientos(
        producto_nombre: String? = null,
        tipo: String? = null,
        cantidad: Int? = null,
        fecha: String? = null
    ): Response<List<Movimiento>> {
        return apiService.searchMovimientos(producto_nombre, tipo, cantidad, fecha)
    }

    suspend fun getProductoPorCodigo(codigo: String): Response<Product> {
        return apiService.getProductoPorCodigo(codigo)
    }

    suspend fun getCategorias(): Response<List<Categoria>> {
        return apiService.getCategorias()
    }

}
