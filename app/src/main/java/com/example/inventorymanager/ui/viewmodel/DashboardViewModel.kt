package com.example.inventorymanager.ui.viewmodel

import androidx.lifecycle.LiveData
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.inventorymanager.data.model.Categoria
import com.example.inventorymanager.data.model.Movimiento
import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class DashboardViewModel(private val repository: ProductRepository? = null) : ViewModel() {

    private val _products = MutableLiveData<List<Product>?>()
    val products: LiveData<List<Product>?> get() = _products

    private val _producto = MutableLiveData<Product?>()
    val producto: LiveData<Product?> get() = _producto

    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias: StateFlow<List<Categoria>> = _categorias


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

    fun realizarMovimiento(productId: Int, tipo: String, cantidad: Int) {
        viewModelScope.launch {
            try {
                val movimiento = Movimiento(
                    id = null, // ID lo genera el backend
                    tipo = tipo,
                    cantidad = cantidad,
                    producto = productId, // Ahora se llama 'producto'
                    fecha = null // Backend puede asignar la fecha
                )
                val response = repository?.createMovimiento(movimiento)
                if (response != null && response.isSuccessful) {
                    Log.d("Movimiento", "Movimiento registrado: ${response.body()}")
                } else {
                    Log.e("Movimiento", "Error en la API: ${response?.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Movimiento", "Error al realizar movimiento: ${e.message}")
            }
        }
    }

    fun buscarProductoPorCodigo(codigo: String) {
        viewModelScope.launch {
            try {
                val response = repository?.getProductoPorCodigo(codigo)
                if (response != null && response.isSuccessful) {
                    _producto.postValue(response.body())
                } else {
                    _producto.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("Error", "Error al buscar producto: ${e.message}")
                _producto.postValue(null)
            }
        }
    }


    fun addProduct(nombre: String, descripcion: String, codigo: String, stock: Int, precio: Double, categoriaId: Int) {
        viewModelScope.launch {
            val nuevoProducto = Product(
                id = 0,
                nombre = nombre,
                descripcion = descripcion,
                codigo = codigo,
                stock = stock,
                precio = precio,
                categoria = categoriaId,
                categoria_nombre = null,
                fecha_creacion = ""
            )

            Log.d("Producto", "Enviando producto: $nuevoProducto")  // ✅ Verificar datos

            repository?.addProduct(nuevoProducto)
            try {
                val response = repository?.addProduct(nuevoProducto)
                if (response != null && response.isSuccessful) {
                    Log.d("Producto", "Producto creado: ${response.body()}")
                } else {
                    Log.e("Producto", "Error al crear producto: ${response?.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Producto", "Excepción al crear producto: ${e.message}")
            }
        }
    }

    fun fetchCategorias() {
        viewModelScope.launch {
            try {
                val response = repository?.getCategorias()
                if (response != null &&response.isSuccessful) {
                    _categorias.value = response.body() ?: emptyList()
                } else {
                    Log.e("Categoría", "Error en la respuesta: ${response?.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Categoría", "Error al cargar categorías: ${e.message}")
            }
        }
    }

    fun buscarProductoYRedirigir(navController: NavController, codigo: String) {
        viewModelScope.launch {
            try {
                val response = repository?.getProductoPorCodigo(codigo)
                val codigoSeguro = URLEncoder.encode(codigo, StandardCharsets.UTF_8.toString())

                if (response != null && response.isSuccessful) {
                    val productoExistente = response.body()

                    if (productoExistente != null) {
                        // ✅ Navegar al detalle si el producto existe
                        navController.navigate("product_detail/$codigoSeguro") {
                            popUpTo("barcode_scanner") { inclusive = true }
                        }
                    } else {
                        // 🚨 Si no existe, redirige a registrar nuevo producto
                        navController.navigate("add_product/$codigoSeguro") {
                            popUpTo("barcode_scanner") { inclusive = true }
                        }
                    }
                } else {
                    navController.navigate("add_product/$codigoSeguro") {
                        popUpTo("barcode_scanner") { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                navController.navigate("add_product/$codigo") {
                    popUpTo("barcode_scanner") { inclusive = true }
                }
            }
        }
    }
}

