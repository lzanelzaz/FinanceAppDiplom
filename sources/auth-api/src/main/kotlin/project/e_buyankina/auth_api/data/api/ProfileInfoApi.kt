package project.e_buyankina.auth_api.data.api

import kotlinx.serialization.Serializable

@Serializable
internal class ProfileInfoApi(
    val username: String,
    val email: String,
)