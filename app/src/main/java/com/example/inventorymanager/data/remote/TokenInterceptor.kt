package com.example.inventorymanager.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import android.content.SharedPreferences
import kotlinx.coroutines.runBlocking

class TokenInterceptor(
    private val sharedPreferences: SharedPreferences,
    private val apiService: ApiService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Obtener el token de acceso actual
        val token = sharedPreferences.getString("access_token", null)
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        // Realizar la solicitud inicial
        val response = chain.proceed(requestBuilder.build())

        // Si el token expira (error 401), intentar refrescarlo
        if (response.code == 401) {
            synchronized(this) {
                val newToken = runBlocking { refreshToken() } // Suspender para obtener un nuevo token
                if (!newToken.isNullOrEmpty()) {
                    // Guardar el nuevo token en SharedPreferences
                    sharedPreferences.edit().putString("access_token", newToken).apply()

                    // Crear una nueva solicitud con el token actualizado
                    val newRequest = chain.request().newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()

                    // Reintentar la solicitud con el nuevo token
                    return chain.proceed(newRequest)
                }
            }
        }

        return response
    }

    private suspend fun refreshToken(): String? {
        val refreshToken = sharedPreferences.getString("refresh_token", null) ?: return null
        return try {
            val response = apiService.refreshToken(mapOf("refresh" to refreshToken))
            if (response.isSuccessful) {
                response.body()?.access
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
