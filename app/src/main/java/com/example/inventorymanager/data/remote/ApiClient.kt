package com.example.inventorymanager.data.remote

import android.content.Context
import com.example.inventorymanager.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.0.249:8000/api/"

    fun getRetrofit(context: Context): Retrofit {
        val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

        // Crea Retrofit sin depender de ApiService aún
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Inicializa ApiService con Retrofit
        val apiService = retrofit.create(ApiService::class.java)

        // Construye OkHttpClient con el interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(sharedPreferences, apiService))
            .build()

        // Devuelve Retrofit configurado con el cliente
        return retrofit.newBuilder()
            .client(client)
            .build()
    }

    fun getApiService(context: Context): ApiService {
        return getRetrofit(context).create(ApiService::class.java)
    }
}



