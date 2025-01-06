package com.example.inventorymanager.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanager.App
import com.example.inventorymanager.App.Companion.context
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.repository.ProductRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: ProductRepository) : ViewModel() {

    fun login(
        email: String,
        password: String,
        onSuccess: (String, String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Llama al metodo login desde la instancia de repository
                val response = repository.login(mapOf("email" to email, "password" to password))
                if (response.isSuccessful) {
                    val body = response.body()
                    val accessToken = body?.access.orEmpty()
                    val refreshToken = body?.refresh.orEmpty()
                    if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                        // Almacena los tokens en SharedPreferences
                        val sharedPreferences = App.context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("access_token", accessToken)
                        editor.putString("refresh_token", refreshToken)
                        editor.apply()

                        onSuccess(accessToken, refreshToken)
                    } else {
                        onFailure(Exception("Tokens inválidos"))
                    }
                } else {
                    onFailure(Exception("Error al iniciar sesión: ${response.code()}"))
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}
