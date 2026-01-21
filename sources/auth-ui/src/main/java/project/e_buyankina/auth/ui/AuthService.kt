package project.e_buyankina.auth.ui

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AuthService {

    @POST("create_user")
    fun createUser(
        @Query("login") login: String,
        @Query("password_hash") password: String,
        @Query("username") username: String,
    )

    @POST("authorize")
    fun authorize(
        @Query("login") login: String,
        @Query("password_hash") password: String,
    )

    @GET("profile")
    fun profileInfo(
        @Query("account_id") accountId: String,
    )
}