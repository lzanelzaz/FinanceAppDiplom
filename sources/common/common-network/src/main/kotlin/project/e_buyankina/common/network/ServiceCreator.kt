package project.e_buyankina.common.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ServiceCreator {

    private val contentType = "application/json".toMediaType()

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_SERVER_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}