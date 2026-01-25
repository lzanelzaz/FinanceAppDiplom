package project.e_buyankina.feature.auth.api.data.api

import kotlinx.serialization.Serializable

@Serializable
internal class ProfileInfoApi(
    val username: String,
    val email: String,
)