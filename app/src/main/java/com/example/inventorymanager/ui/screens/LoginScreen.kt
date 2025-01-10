package com.example.inventorymanager.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventorymanager.R
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.remote.RetrofitClient
import com.example.inventorymanager.data.repository.ProductRepository
import com.example.inventorymanager.ui.theme.InventoryManagerTheme
import com.example.inventorymanager.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit = {}) {
    val context = LocalContext.current

    // Inicialización de Retrofit y los repositorios
    val apiService = remember { RetrofitClient.getClient(context).create(ApiService::class.java) }
    val productRepository = remember { ProductRepository(apiService) }
    val loginViewModel = remember { LoginViewModel(productRepository) }

    // Variables para el usuario y contraseña
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundShapes()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icono en el centro
            Image(
                painter = painterResource(R.drawable.inventory), // Reemplaza con tu icono
                contentDescription = "Center Icon",
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para el nombre de usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.mdi__email),
                        contentDescription = "Email Icon",
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para la contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.mdi__eye_off),
                        contentDescription = "Password Icon",
                        modifier = Modifier.size(20.dp)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botón de inicio de sesión
            Button(
                onClick = {
                    loginViewModel.login(
                        email = username,
                        password = password,
                        onSuccess = { _, _ ->
                            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        },
                        onFailure = { error ->
                            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }
        }
    }
}

@Composable
fun BackgroundShapes(modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = modifier.fillMaxSize()) {
        // Dibuja un cuadrado muy grande en la parte inferior derecha
        drawRect(
            color = secondaryColor,
            topLeft = Offset(x = size.width * 0.2f, y = size.height * 0.4f),
            size = androidx.compose.ui.geometry.Size(1000f, 1000f)
        )

        // Dibuja un círculo muy grande en la parte superior izquierda
        drawCircle(
            color = primaryColor,
            radius = 600f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.10f)
        )

        // Dibuja un círculo adicional en la parte superior derecha
        drawCircle(
            color = tertiaryColor,
            radius = 400f,
            center = Offset(x = size.width * 0.8f, y = size.height * 0.5f)
        )

        // Dibuja un círculo adicional en el centro
        drawCircle(
            color = primaryColor,
            radius = 300f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.4f)
        )

        // Dibuja un círculo adicional en la parte inferior izquierda
        drawCircle(
            color = secondaryColor,
            radius = 1000f,
            center = Offset(x = size.width * 0.2f, y = size.height * 0.8f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    InventoryManagerTheme {
        LoginScreen()
    }
}