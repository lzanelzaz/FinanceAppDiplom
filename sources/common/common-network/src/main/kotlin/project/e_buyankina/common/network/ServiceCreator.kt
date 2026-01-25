package project.e_buyankina.common.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ServiceCreator {

    private val contentType = "application/json".toMediaType()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://ebuyankina/api/")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}