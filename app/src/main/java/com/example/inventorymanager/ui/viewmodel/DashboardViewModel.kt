package com.example.inventorymanager.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.repository.ProductRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: ProductRepository? = null) : ViewModel() {

    private val _products = MutableLiveData<List<Product>?>()
    val products: LiveData<List<Product>?> get() = _products

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = repository?.getProducts() // Esto devuelve Response<List<Product>>
                if (response?.isSuccessful == true) {
                    val products = response.body() // Extrae el cuerpo de la respuesta
                    if (products != null) {
                        _products.postValue(products) // Actualiza los datos de LiveData
                    } else {
                        _products.postValue(emptyList()) // En caso de que el cuerpo sea nulo
                    }
                } else {
                    _products.postValue(emptyList()) // En caso de que la respuesta no sea exitosa
                    println("Error al obtener productos: ${response?.code()}")
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
                println("Excepción al obtener productos: ${e.message}")
            }
        }

        _products.value = listOf(
            Product(
                id = 1,
                nombre = "Producto 1",
                precio = 10.0,
                stock = 100,
                descripcion = "Descripción del Producto 1",
                categoria_nombre = "Categoría 1",
                codigo = "P001",
                categoria = "Categoría 1",
                fecha_creacion = "2023-10-01"
            ),
            Product(
                id = 2,
                nombre = "Producto 2",
                precio = 20.0,
                stock = 200,
                descripcion = "Descripción del Producto 2",
                categoria_nombre = "Categoría 2",
                codigo = "P002",
                categoria = "Categoría 2",
                fecha_creacion = "2023-10-01"
            )
        )
    }

    fun login(username: String, password: String, onSuccess: (String, String) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository?.login(mapOf("username" to username, "password" to password))
                if (response?.isSuccessful == true) {
                    val body = response.body()
                    val accessToken = body?.access.orEmpty()
                    val refreshToken = body?.refresh.orEmpty()
                    if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                        onSuccess(accessToken, refreshToken)
                    } else {
                        onFailure(Exception("Tokens inválidos"))
                    }
                } else {
                    onFailure(Exception("Error al iniciar sesión: ${response?.code()}"))
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    fun searchProducts(
        nombre: String? = null,
        categoria: String? = null,
        precioMin: Double? = null,
        precioMax: Double? = null
    ) {
        viewModelScope.launch {
            try {
                val response = repository?.searchProducts(nombre, categoria, precioMin, precioMax)
                if (response?.isSuccessful == true) {
                    val products = response.body()
                    _products.postValue(products ?: emptyList())
                } else {
                    _products.postValue(emptyList())
                    println("Error en la búsqueda avanzada: ${response?.code()}")
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
                println("Excepción en la búsqueda avanzada: ${e.message}")
            }
        }
    }
}