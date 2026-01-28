package project.e_buyankina.feature.operations.api.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import project.e_buyankina.common.network.serializers.BigDecimalSerializer
import project.e_buyankina.common.network.serializers.DateSerializer
import java.math.BigDecimal
import java.time.LocalDate

@Serializable
internal class OperationApi(

    @SerialName("id")
    val operationId: String,

    @SerialName("type")
    val type: TransactionTypeApi,

    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("amount")
    val amount: BigDecimal,

    @Serializable(with = DateSerializer::class)
    @SerialName("date")
    val date: LocalDate,

    @SerialName("subtype")
    val subtype: String,

    @SerialName("description")
    val description: String? = null,
)