package com.example.inventorymanager

import android.app.Application
import android.content.Context

/**
 * 📱 Clase Application para proporcionar un contexto global en toda la aplicación.
 *
 * Esta clase se inicializa cuando la aplicación se inicia y permite acceder
 * al contexto de la aplicación desde cualquier parte del proyecto.
 */
class App : Application() {

    init {
        // 🔄 Inicializa la instancia de la aplicación cuando se crea
        instance = this
    }

    companion object {
        // 🏷️ Instancia única de la aplicación
        private var instance: App? = null

        /**
         * 🌐 Proporciona el contexto global de la aplicación.
         *
         * @return Contexto de la aplicación.
         */
        val context: Context
            get() = instance!!.applicationContext
    }
}
