// ==============================
// Modelo de respuesta del token
// ==============================
package com.example.inventorymanager.data.model

// Representa la respuesta del backend al autenticar o refrescar un token
data class TokenResponse(
    val access: String,  // Token de acceso para autenticación
    val refresh: String  // Token de refresco para obtener un nuevo access token
)
