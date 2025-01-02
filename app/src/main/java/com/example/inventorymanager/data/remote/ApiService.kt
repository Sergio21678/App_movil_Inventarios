package com.example.inventorymanager.data.remote

import android.telecom.Call
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.model.TokenResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.DELETE


interface ApiService {

    @POST("token/refresh/")
    suspend fun refreshToken(@Body body: Map<String, String>): Response<TokenResponse>

    @GET("api/productos/")
    suspend fun getProducts(): Response<List<Product>>

    @POST("products")
    suspend fun addProduct(@Body product: Product): Response<Product>

    @POST("token/")
    suspend fun login(@Body body: Map<String, String>): Response<TokenResponse>

    @DELETE("products/{id}/delete")
    suspend fun deleteProduct(@Path("id") productId: Int): Response<Unit>


}