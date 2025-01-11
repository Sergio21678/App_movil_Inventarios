package com.example.inventorymanager.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inventorymanager.App.Companion.context
import com.example.inventorymanager.ui.viewmodel.MovimientosViewModel
import java.util.Calendar

@Composable
fun MovimientosScreen(
    navController: NavController,
    viewModel: MovimientosViewModel,
    modifier: Modifier = Modifier
) {
    val movimientos by viewModel.movimientos.observeAsState(emptyList())
    val context = LocalContext.current

    // Variables para búsqueda
    var searchQuery by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf("") }
    var cantidadQuery by remember { mutableStateOf("") }
    var fechaQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val opcionesTipo = listOf("entrada", "salida")

    // Ejecuta la búsqueda automática cuando cambian los filtros
    LaunchedEffect(searchQuery, tipoSeleccionado, cantidadQuery, fechaQuery) {
        viewModel.searchMovimientos(
            producto_nombre = searchQuery,
            tipo = tipoSeleccionado,
            cantidad = cantidadQuery.toIntOrNull(),
            fecha = fechaQuery
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        // Búsqueda por nombre de producto
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar por producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Búsqueda por tipo con menú desplegable
        Box {
            OutlinedTextField(
                value = if (tipoSeleccionado.isNotEmpty()) tipoSeleccionado else "Selecciona tipo",
                onValueChange = {},
                label = { Text("Tipo de movimiento") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Expandir")
                    }
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcionesTipo.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo.capitalize()) },  // Muestra el texto del tipo
                        onClick = {
                            tipoSeleccionado = tipo
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Búsqueda por cantidad
        OutlinedTextField(
            value = cantidadQuery,
            onValueChange = { cantidadQuery = it },
            label = { Text("Buscar por cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Selector de fecha con DatePickerDialog
        OutlinedTextField(
            value = fechaQuery,
            onValueChange = {},
            label = { Text("Seleccionar fecha") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context,  // ✅ Usando contexto seguro
                        { _, year, month, dayOfMonth ->
                            fechaQuery = "$year-${month + 1}-$dayOfMonth"
                            viewModel.searchMovimientos(
                                producto_nombre = searchQuery,
                                tipo = tipoSeleccionado,
                                cantidad = cantidadQuery.toIntOrNull(),
                                fecha = fechaQuery
                            )
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de movimientos
        LazyColumn {
            items(movimientos) { movimiento ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Producto: ${movimiento.producto_nombre}")
                        Text("Tipo: ${movimiento.tipo}")
                        Text("Cantidad: ${movimiento.cantidad}")
                        Text("Fecha: ${movimiento.fecha}")
                    }
                }
            }
        }
    }
}