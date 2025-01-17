package com.example.inventorymanager.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.runBlocking

// Clase que intercepta las solicitudes HTTP para agregar el token de autenticación
class TokenInterceptor(
    private val sharedPreferences: SharedPreferences, // Almacena los tokens de acceso y refresh
    private val apiService: ApiService                // Servicio para refrescar el token
) : Interceptor {

    // Método que intercepta todas las solicitudes HTTP
    override fun intercept(chain: Interceptor.Chain): Response {
        // Crea una nueva solicitud basada en la original
        val requestBuilder = chain.request().newBuilder()

        // Obtiene el token de acceso almacenado
        val token = sharedPreferences.getString("access_token", null)
        token?.trim()?.let {
            // Si hay token, lo agrega en los encabezados de la solicitud
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        // Envía la solicitud y recibe la respuesta
        val response = chain.proceed(requestBuilder.build())

        // Verifica si la respuesta indica que el token expiró (error 401)
        if (response.code == 401) {     // Evita múltiples intentos simultáneos de refrescar el token
            synchronized(this) {   // Cierra la respuesta antes de refrescar el token
                response.close() // Cerrar la respuesta antes de refrescar

                // Intenta refrescar el token de forma síncrona
                val newToken = runBlocking { refreshToken() }
                if (!newToken.isNullOrEmpty()) {
                    // Si el nuevo token es válido, lo guarda en SharedPreferences
                    sharedPreferences.edit().putString("access_token", newToken).apply()

                    // Crea una nueva solicitud con el token actualizado
                    val newRequest = chain.request().newBuilder()
                        .removeHeader("Authorization")                          // Elimina el encabezado viejo
                        .addHeader("Authorization", "Bearer $newToken")   // Agrega el nuevo token
                        .build()

                    // Reintenta la solicitud con el nuevo token
                    return chain.proceed(newRequest)
                } else {
                    Log.e("TokenInterceptor", "No se pudo refrescar el token")
                    // Opcional: Manejar la expiración del refresh token aquí.
                }
            }
        }

        // Devuelve la respuesta original si el token no expiró
        return response
    }

    // Método que solicita un nuevo token de acceso usando el refresh token
    private suspend fun refreshToken(): String? {
        // Obtiene el refresh token de SharedPreferences
        val refreshToken = sharedPreferences.getString("refresh_token", null) ?: return null
        return try {
            // Solicita un nuevo token al backend
            val response = apiService.refreshToken(mapOf("refresh" to refreshToken))
            if (response.isSuccessful) {
                // Si la respuesta es exitosa, devuelve el nuevo token de acceso
                response.body()?.access
            } else {
                // Si falla, registra el error
                Log.e("TokenInterceptor", "Error al refrescar el token: ${response.code()} ${response.message()}")
                null
            }
        } catch (e: Exception) {
            // Maneja cualquier excepción que ocurra durante la solicitud
            Log.e("TokenInterceptor", "Excepción al refrescar el token", e)
            null
        }
    }

}

