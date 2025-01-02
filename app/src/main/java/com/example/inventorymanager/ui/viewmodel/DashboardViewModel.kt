package com.example.inventorymanager.ui.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.repository.ProductRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class DashboardViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getProducts() // Esto devuelve Response<List<Product>>
                if (response.isSuccessful) {
                    val products = response.body() // Extrae el cuerpo de la respuesta
                    if (products != null) {
                        _products.postValue(products) // Actualiza los datos de LiveData
                    } else {
                        _products.postValue(emptyList()) // En caso de que el cuerpo sea nulo
                    }
                } else {
                    _products.postValue(emptyList()) // En caso de que la respuesta no sea exitosa
                    println("Error al obtener productos: ${response.code()}")
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
                println("Excepción al obtener productos: ${e.message}")
            }
        }
    }


    fun login(username: String, password: String, onSuccess: (String, String) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(mapOf("username" to username, "password" to password))
                if (response.isSuccessful) {
                    val body = response.body()
                    val accessToken = body?.access.orEmpty()
                    val refreshToken = body?.refresh.orEmpty()
                    if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
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

