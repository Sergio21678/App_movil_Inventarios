package com.example.inventorymanager.data.remote

import android.telecom.Call
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


interface ApiService {

    @GET("productos/busqueda/")
    suspend fun searchProducts(
        @Query("nombre") nombre: String? = null,
        @Query("categoria") categoria: String? = null,
        @Query("precio_min") precioMin: Double? = null,
        @Query("precio_max") precioMax: Double? = null
    ): Response<List<Product>>

    @GET("movimientos/")
    suspend fun getMovimientos(): Response<List<Movimiento>>

    @POST("token/refresh/")
    suspend fun refreshToken(@Body body: Map<String, String>): Response<TokenResponse>

    @GET("productos/")
    suspend fun getProducts(): Response<List<Product>>

    @POST("products")
    suspend fun addProduct(@Body product: Product): Response<Product>

    @POST("token/")
    suspend fun login(@Body credentials: Map<String, String>): Response<TokenResponse>

    @DELETE("products/{id}/delete")
    suspend fun deleteProduct(@Path("id") productId: Int): Response<Unit>

    @GET("api/products/{id}/")
    suspend fun getProductById(@Path("id") productId: Int): Response<Product>

    @POST("movimientos/")
    suspend fun createMovimiento(
        @Body movimiento: Movimiento
    ): Response<Movimiento>

    @POST("movimientos/")
    suspend fun registrarMovimiento(
        @Query("producto_id") productoId: Int,
        @Query("tipo") tipo: String,
        @Query("cantidad") cantidad: Int
    ): Response<Unit>

    @POST("movimientos/")
    suspend fun addMovimiento(
        @Field("producto") productId: Int,
        @Field("tipo") tipo: String,
        @Field("cantidad") cantidad: Int
    ): Response<Unit>

}