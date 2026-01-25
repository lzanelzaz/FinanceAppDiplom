package project.e_buyankina.auth_api.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AuthService {

    @POST("create_user")
    suspend fun createUser(
        @Query("login") login: String,
        @Query("password_hash") password: String,
        @Query("username") username: String,
    ): Response<AuthorizeInfoApi>

    @POST("authorize")
    suspend fun authorize(
        @Query("login") login: String,
        @Query("password_hash") password: String,
    ): Response<AuthorizeInfoApi>

    @GET("profile")
    suspend fun profileInfo(
        @Query("account_id") accountId: String,
    ): Response<ProfileInfoApi>
}