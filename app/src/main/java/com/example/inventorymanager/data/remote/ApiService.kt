package com.example.inventorymanager.data.remote

import android.telecom.Call
import com.example.inventorymanager.data.model.Categoria
import com.example.inventorymanager.data.model.Movimiento
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.model.TokenResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.Query

// Interfaz para definir las llamadas a la API REST utilizando Retrofit
interface ApiService {

    // 🔎 Búsqueda avanzada de productos según nombre, categoría y rango de precios
    @GET("productos/busqueda/")
    suspend fun searchProducts(
        @Query("nombre") nombre: String? = null,              // Filtro por nombre del producto
        @Query("categoria") categoria: String? = null,        // Filtro por categoría
        @Query("precio_min") precioMin: Double? = null,       // Filtro por precio mínimo
        @Query("precio_max") precioMax: Double? = null        // Filtro por precio máximo
    ): Response<List<Product>>

    // 🔎 Obtener todos los movimientos registrados
    @GET("movimientos/")
    suspend fun getMovimientos(): Response<List<Movimiento>>

    // 🔄 Refrescar el token de autenticación
    @POST("token/refresh/")
    suspend fun refreshToken(@Body body: Map<String, String>): Response<TokenResponse>

    // 🔎 Obtener todos los productos
    @GET("productos/")
    suspend fun getProducts(): Response<List<Product>>

    // ➕ Agregar un nuevo producto
    @POST("productos/")
    suspend fun addProduct(@Body product: Product): Response<Product>

    // 🔑 Iniciar sesión para obtener tokens
    @POST("token/")
    suspend fun login(@Body credentials: Map<String, String>): Response<TokenResponse>

    // ❌ Eliminar un producto por su ID
    @DELETE("products/{id}/delete")
    suspend fun deleteProduct(@Path("id") productId: Int): Response<Unit>

    // 🔎 Obtener detalles de un producto por su ID
    @GET("api/products/{id}/")
    suspend fun getProductById(@Path("id") productId: Int): Response<Product>

    // 🔍 Buscar movimientos por nombre de producto
    @GET("movimientos/search")
    suspend fun searchMovimientos(@Query("producto_nombre") productoNombre: String): Response<List<Movimiento>>

    // ➕ Crear un movimiento de inventario
    @POST("movimientos/")
    suspend fun createMovimiento(
        @Body movimiento: Movimiento
    ): Response<Movimiento>

    // ➕ Registrar un movimiento especificando detalles mediante query params
    @POST("movimientos/")
    suspend fun registrarMovimiento(
        @Query("producto_id") productoId: Int,
        @Query("tipo") tipo: String,
        @Query("cantidad") cantidad: Int
    ): Response<Unit>

    // ➕ Alternativa para agregar un movimiento utilizando campos en el cuerpo
    @POST("movimientos/")
    suspend fun addMovimiento(
        @Field("producto") productId: Int,
        @Field("tipo") tipo: String,
        @Field("cantidad") cantidad: Int
    ): Response<Unit>

    // 🔍 Búsqueda avanzada de movimientos con múltiples filtros
    @GET("movimientos/busqueda/")
    suspend fun searchMovimientos(
        @Query("producto_nombre") producto: String? = null,
        @Query("tipo") tipo: String? = null,
        @Query("cantidad") cantidad: Int? = null,
        @Query("fecha") fecha: String? = null
    ): Response<List<Movimiento>>

    // 🔎 Buscar producto por su código único
    @GET("productos/codigo/{codigo}/")
    suspend fun getProductoPorCodigo(@Path("codigo") codigo: String): Response<Product>

    // 🔎 Obtener todas las categorías disponibles
    @GET("categorias/")
    suspend fun getCategorias(): Response<List<Categoria>>
}