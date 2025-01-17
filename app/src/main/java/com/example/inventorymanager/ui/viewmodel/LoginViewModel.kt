package com.example.inventorymanager.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanager.App
import com.example.inventorymanager.App.Companion.context
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.repository.ProductRepository
import kotlinx.coroutines.launch

// ✅ ViewModel para gestionar el proceso de inicio de sesión
class LoginViewModel(private val repository: ProductRepository) : ViewModel() {

    /**
     * 🔐 Función para iniciar sesión en la aplicación.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param onSuccess Callback que se ejecuta si el inicio de sesión es exitoso.
     * @param onFailure Callback que se ejecuta si ocurre un error durante el inicio de sesión.
     */

    fun login(
        email: String,
        password: String,
        onSuccess: (String, String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        // 🔄 Ejecutar la llamada a la API de forma asíncrona
        viewModelScope.launch {
            try {
                // 📞 Llamada al método de login desde el repositorio
                val response = repository.login(mapOf("email" to email, "password" to password))

                // ✅ Verificar si la respuesta fue exitosa
                if (response.isSuccessful) {
                    val body = response.body()
                    val accessToken = body?.access.orEmpty()
                    val refreshToken = body?.refresh.orEmpty()

                    // 🔑 Verificar si los tokens no están vacíos
                    if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                        // 📝 Almacenar los tokens en SharedPreferences
                        val sharedPreferences = App.context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("access_token", accessToken)
                        editor.putString("refresh_token", refreshToken)
                        editor.apply()

                        // 🚀 Llamar al callback de éxito
                        onSuccess(accessToken, refreshToken)
                    } else {
                        // ❌ Error si los tokens son inválidos
                        onFailure(Exception("Tokens inválidos"))
                    }
                } else {
                    // ❌ Error si la respuesta no fue exitosa
                    onFailure(Exception("Error al iniciar sesión: ${response.code()}"))
                }
            } catch (e: Exception) {
                // ⚠️ Captura cualquier excepción durante la solicitud
                onFailure(e)
            }
        }
    }
}