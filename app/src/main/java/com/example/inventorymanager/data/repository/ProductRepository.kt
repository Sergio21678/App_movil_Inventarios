// ===============================================
// Repositorio de Productos para manejar la lógica
// ===============================================

package com.example.inventorymanager.data.repository

import com.example.inventorymanager.data.model.Categoria
import com.example.inventorymanager.data.model.Movimiento
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.model.TokenResponse
import com.example.inventorymanager.data.remote.ApiService
import retrofit2.Response

// Clase que actúa como intermediario entre la API y el ViewModel
class ProductRepository(private val apiService: ApiService) {

    // Obtener la lista de productos
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

    // Buscar productos con filtros
    suspend fun searchProducts(
        nombre: String? = null,      // Filtrar por nombre
        categoria: String? = null,   // Filtrar por categoría
        precioMin: Double? = null,   // Filtrar por precio mínimo
        precioMax: Double? = null    // Filtrar por precio máximo
    ): Response<List<Product>> {
        // Llama al método searchProducts del ApiService con filtros opcionales
        return apiService.searchProducts(nombre, categoria, precioMin, precioMax)
    }

    // Autenticación de usuario (Login)
    suspend fun login(credentials: Map<String, String>) = apiService.login(credentials)

    // Obtener todos los movimientos de inventario
    suspend fun getMovimientos(): Response<List<Movimiento>> {
        // Llama al método getMovimientos del ApiService para obtener los movimientos
        return apiService.getMovimientos()
    }

    // Obtener el nombre de un producto por su ID
    suspend fun getProductoNombre(productoId: Int): String? {
        return try {
            val response = apiService.getProductById(productoId) // Consulta por ID
            if (response.isSuccessful) {
                response.body()?.nombre // Devuelve el nombre si es exitoso
            } else {
                null // Retorna null si falla
            }
        } catch (e: Exception) {
            println("Error al obtener el nombre del producto: ${e.message}")
            null
        }
    }

    // Crear un nuevo movimiento de inventario
    suspend fun createMovimiento(movimiento: Movimiento): Response<Movimiento> {
        // Llama al método createMovimiento del ApiService
        return apiService.createMovimiento(movimiento)
    }

    // Registrar un movimiento con datos individuales
    suspend fun registrarMovimiento(productoId: Int, tipo: String, cantidad: Int): Response<Unit> {
        // Llama al método registrarMovimiento del ApiService
        return apiService.registrarMovimiento(productoId, tipo, cantidad)
    }

    // Agregar un movimiento (forma alternativa)
    suspend fun addMovimiento(productId: Int, tipo: String, cantidad: Int): Response<Unit> {
        // Llama al método addMovimiento del ApiService
        return apiService.addMovimiento(productId, tipo, cantidad)
    }

    // Buscar movimientos por tipo, nombre de producto o fecha
    suspend fun searchMovimientos(
        producto_nombre: String? = null,  // Filtrar por nombre del producto
        tipo: String? = null,             // Filtrar por tipo de movimiento
        cantidad: Int? = null,            // Filtrar por cantidad
        fecha: String? = null             // Filtrar por fecha
    ): Response<List<Movimiento>> {
        // Llama al método searchMovimientos del ApiService con filtros
        return apiService.searchMovimientos(producto_nombre, tipo, cantidad, fecha)
    }

    // Buscar un producto por su código
    suspend fun getProductoPorCodigo(codigo: String): Response<Product> {
        // Llama al método getProductoPorCodigo del ApiService
        return apiService.getProductoPorCodigo(codigo)
    }

    // Obtener todas las categorías
    suspend fun getCategorias(): Response<List<Categoria>> {
        // Llama al método getCategorias del ApiService
        return apiService.getCategorias()
    }
}
