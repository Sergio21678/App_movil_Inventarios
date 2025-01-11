package com.example.inventorymanager.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanager.data.model.Movimiento
import com.example.inventorymanager.data.repository.ProductRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class MovimientosViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _movimientos = MutableLiveData<List<Movimiento>>()
    val movimientos: LiveData<List<Movimiento>> get() = _movimientos

    private fun formatFecha(fecha: String?): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(fecha ?: "") // Maneja valores nulos
            outputFormat.format(date ?: "")
        } catch (e: Exception) {
            "Fecha no válida"
        }
    }

    fun fetchMovimientos() {
        viewModelScope.launch {
            try {
                val response = repository.getMovimientos()
                if (response.isSuccessful) {
                    val movimientosConFormato = response.body()?.map { movimiento ->
                        movimiento.copy(fecha = formatFecha(movimiento.fecha))
                    } ?: emptyList()
                    _movimientos.postValue(movimientosConFormato)
                } else {
                    _movimientos.postValue(emptyList())
                    println("Error al obtener movimientos: ${response.code()}")
                }
            } catch (e: Exception) {
                _movimientos.postValue(emptyList())
                println("Excepción al obtener movimientos: ${e.message}")
            }
        }
    }

    fun searchMovimientos(
        producto_nombre: String? = null,
        tipo: String? = null,
        cantidad: Int? = null,
        fecha: String? = null

    ) {
        viewModelScope.launch {
            try {
                val response = repository.searchMovimientos(producto_nombre, tipo, cantidad, fecha)
                if (response.isSuccessful) {
                    _movimientos.postValue(response.body() ?: emptyList())
                } else {
                    println("Error al buscar movimientos: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Excepción al buscar movimientos: ${e.message}")
            }
        }
    }


}
