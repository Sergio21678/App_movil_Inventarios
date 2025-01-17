package com.example.inventorymanager.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context

// Objeto singleton que configura y proporciona la instancia de Retrofit
object RetrofitClient {

    // Instancia de Retrofit (se inicializa solo una vez)
    private var retrofit: Retrofit? = null

    // Método para obtener la instancia de Retrofit
    fun getClient(context: Context): Retrofit {
        if (retrofit == null) {

            // Accede a las SharedPreferences para obtener tokens almacenados
            val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

            // Crea una instancia de ApiService para el uso del TokenInterceptor
            val apiService = createApiService()

            // Crea el interceptor de token para agregar autenticación a las solicitudes
            val tokenInterceptor = TokenInterceptor(sharedPreferences, apiService)

            // Configura OkHttpClient con el TokenInterceptor para manejar autenticación
            val client = OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor) // Agrega el interceptor de token
                .build()

            // Configura Retrofit con la URL base, el cliente HTTP y el convertidor Gson
            retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.129.49:8000/api/")         // URL base del servidor backend
                .client(client)                                     // Cliente HTTP configurado
                .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON
                .build()
        }
        // Devuelve la instancia de Retrofit
        return retrofit!!
    }

    // Método privado para crear una instancia básica de ApiService sin interceptor
    private fun createApiService(): ApiService {
        // Cliente HTTP simple sin interceptor
        val client = OkHttpClient.Builder().build()

        // Configura Retrofit con la URL base y el convertidor Gson
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.129.49:8000/api/")             // URL base del servidor backende
            .client(client)                                         // Cliente HTTP sin interceptor
            .addConverterFactory(GsonConverterFactory.create())     // Convertidor JSON
            .build()

        // Crea y devuelve la instancia de ApiService
        return retrofit.create(ApiService::class.java)
    }
}
