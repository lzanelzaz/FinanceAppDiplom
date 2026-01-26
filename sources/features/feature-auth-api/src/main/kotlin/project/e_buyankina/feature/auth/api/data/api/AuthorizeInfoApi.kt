package project.e_buyankina.feature.auth.api.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class AuthorizeInfoApi(

    @SerialName("account_id")
    val accountId: String,
)