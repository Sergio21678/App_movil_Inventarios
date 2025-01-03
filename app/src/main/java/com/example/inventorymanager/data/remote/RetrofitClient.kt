import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import com.example.inventorymanager.data.remote.ApiService
import com.example.inventorymanager.data.remote.TokenInterceptor

object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

            // Crear instancia de ApiService para el TokenInterceptor
            val apiService = createApiService()

            val tokenInterceptor = TokenInterceptor(sharedPreferences, apiService)

            val client = OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.249:8000/api/") // Cambia por tu URL base
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    private fun createApiService(): ApiService {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.249:8000/api/") // Cambia por tu URL base
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
