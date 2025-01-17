package com.example.inventorymanager

import android.app.Application
import android.content.Context

/**
 * 🌟 Clase MyApplication
 *
 * Esta clase extiende de [Application] y se utiliza para mantener el contexto global
 * de la aplicación, permitiendo el acceso desde cualquier parte del proyecto.
 */
class MyApplication : Application() {

    companion object {
        // 🔗 Contexto global de la aplicación
        lateinit var context: Context
    }

    /**
     * 🚀 Método que se ejecuta cuando la aplicación se inicia.
     * Inicializa el contexto global de la aplicación.
     */
    override fun onCreate() {
        super.onCreate()

        // ✅ Asigna el contexto de la aplicación a la variable global
        context = applicationContext
    }
}
