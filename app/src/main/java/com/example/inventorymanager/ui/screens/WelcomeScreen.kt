package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inventorymanager.R
import com.example.inventorymanager.ui.navigation.Screen
import com.example.inventorymanager.ui.theme.InventoryManagerTheme

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        WelcomeBackgroundShapes() // Add the background shapes

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.inventory), //Aqui va un icono
                contentDescription = "Inventory Logo",
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Bienvenido a Inventory Manager",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate(Screen.Dashboard.route) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ir al Dashboard", color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}

@Composable
fun WelcomeBackgroundShapes(modifier: Modifier = Modifier) {
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    val secondaryColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)

    Canvas(modifier = modifier.fillMaxSize()) {
        // Dibuja un cuadrado grande en la parte inferior derecha con transparencia
        drawRect(
            color = secondaryColor,
            topLeft = Offset(x = size.width * 0.8f, y = size.height * 0.7f),
            size = androidx.compose.ui.geometry.Size(1000f, 1000f)
        )

        // Dibuja un círculo grande en la parte superior izquierda con transparencia
        drawCircle(
            color = primaryColor,
            radius = 650f,
            center = Offset(x = size.width * 0.3f, y = size.height * 0.2f)
        )

        // Dibuja un círculo adicional en el centro con transparencia
        drawCircle(
            color = primaryColor,
            radius = 800f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    InventoryManagerTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}