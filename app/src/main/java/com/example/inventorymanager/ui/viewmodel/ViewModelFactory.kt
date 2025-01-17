package com.example.inventorymanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventorymanager.data.repository.ProductRepository

/**
 * 🏭 Fábrica para crear instancias de MovimientosViewModel con el repositorio inyectado.
 *
 * @param repository Repositorio de productos para manejar las operaciones de datos.
 */
class MovimientosViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    /**
     * 🔄 Crea una instancia de MovimientosViewModel.
     *
     * @param modelClass Clase del ViewModel que se desea crear.
     * @return Instancia de MovimientosViewModel con el repositorio inyectado.
     * @throws IllegalArgumentException si la clase no es MovimientosViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // ✅ Verifica si la clase solicitada es MovimientosViewModel
        if (modelClass.isAssignableFrom(MovimientosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovimientosViewModel(repository) as T
        }
        // ⚠️ Lanza una excepción si la clase no es compatible
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}