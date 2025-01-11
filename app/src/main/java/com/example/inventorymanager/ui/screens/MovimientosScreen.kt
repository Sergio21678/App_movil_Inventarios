package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inventorymanager.ui.viewmodel.MovimientosViewModel

@Composable
fun MovimientosScreen(
    navController: NavController,
    viewModel: MovimientosViewModel,
    modifier: Modifier = Modifier // Declarar el parámetro `modifier`
) {
    val movimientos = viewModel.movimientos.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchMovimientos()
    }

    Column(
        modifier = modifier // Utilizar el `modifier`
            .padding(16.dp)
    ) {
        Text(
            text = "Movimientos de Inventario",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(movimientos.value) { movimiento ->
                Card(
                    modifier = Modifier.padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Producto: ${movimiento.producto_nombre ?: "Desconocido"}")
                        Text(text = "Cantidad: ${movimiento.cantidad}")
                        Text(text = "Tipo: ${movimiento.tipo}")
                        Text(text = "Fecha: ${movimiento.fecha}")
                    }
                }
            }
        }

    }
}
