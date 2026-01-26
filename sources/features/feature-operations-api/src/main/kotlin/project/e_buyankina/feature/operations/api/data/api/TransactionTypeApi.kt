package project.e_buyankina.feature.operations.api.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TransactionTypeApi {

    @SerialName("expense")
    EXPENSE,

    @SerialName("income")
    INCOME;
}
