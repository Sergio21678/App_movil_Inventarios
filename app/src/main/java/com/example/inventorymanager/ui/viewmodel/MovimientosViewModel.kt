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

/**
 * 🎛️ ViewModel encargado de gestionar la lógica de negocio para los movimientos de inventario.
 */
open class MovimientosViewModel(private val repository: ProductRepository) : ViewModel() {

    // 🔄 LiveData para observar la lista de movimientos
    val _movimientos = MutableLiveData<List<Movimiento>>()
    val movimientos: LiveData<List<Movimiento>> get() = _movimientos

    /**
     * 📅 Formatea la fecha recibida del backend a un formato legible.
     *
     * @param fecha Fecha en formato ISO.
     * @return Fecha formateada en "dd-MM-yyyy HH:mm" o mensaje de error.
     */
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

    /**
     * 🔄 Obtiene todos los movimientos del inventario desde el backend.
     */
    fun fetchMovimientos() {
        viewModelScope.launch {
            try {
                val response = repository.getMovimientos()
                if (response.isSuccessful) {
                    // ✅ Formatea la fecha de cada movimiento antes de actualizar el LiveData
                    val movimientosConFormato = response.body()?.map { movimiento ->
                        movimiento.copy(fecha = formatFecha(movimiento.fecha))
                    } ?: emptyList()

                    _movimientos.postValue(movimientosConFormato)
                } else {
                    // ⚠️ Manejo de error en caso de respuesta fallida
                    _movimientos.postValue(emptyList())
                    println("Error al obtener movimientos: ${response.code()}")
                }
            } catch (e: Exception) {
                // ❌ Manejo de excepciones durante la solicitud
                _movimientos.postValue(emptyList())
                println("Excepción al obtener movimientos: ${e.message}")
            }
        }
    }

    /**
     * 🔎 Realiza la búsqueda de movimientos según filtros específicos.
     *
     * @param producto_nombre Nombre del producto a buscar.
     * @param tipo Tipo de movimiento (entrada, salida).
     * @param cantidad Cantidad exacta a buscar.
     * @param fecha Fecha exacta del movimiento.
     */
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
                    // ✅ Actualiza los movimientos con los resultados de la búsqueda
                    _movimientos.postValue(response.body() ?: emptyList())
                } else {
                    // ⚠️ Manejo de error en caso de respuesta fallida
                    println("Error al buscar movimientos: ${response.code()}")
                }
            } catch (e: Exception) {
                // ❌ Manejo de excepciones durante la búsqueda
                println("Excepción al buscar movimientos: ${e.message}")
            }
        }
    }
}