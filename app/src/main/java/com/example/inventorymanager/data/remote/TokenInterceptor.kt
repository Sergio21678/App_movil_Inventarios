package com.example.inventorymanager.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.runBlocking

class TokenInterceptor(
    private val sharedPreferences: SharedPreferences,
    private val apiService: ApiService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = sharedPreferences.getString("access_token", null)
        token?.trim()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            synchronized(this) {
                val newToken = runBlocking { refreshToken() }
                if (!newToken.isNullOrEmpty()) {
                    sharedPreferences.edit().putString("access_token", newToken).apply()

                    val newRequest = chain.request().newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()

                    return chain.proceed(newRequest)
                } else {
                    Log.e("TokenInterceptor", "No se pudo refrescar el token")
                    // Opcional: Manejar la expiración del refresh token aquí.
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
                Log.e("TokenInterceptor", "Error al refrescar el token: ${response.code()} ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("TokenInterceptor", "Excepción al refrescar el token", e)
            null
        }
    }

}

