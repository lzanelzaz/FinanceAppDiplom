package project.e_buyankina.feature.auth.api.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ProfileInfoApi(

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,
)