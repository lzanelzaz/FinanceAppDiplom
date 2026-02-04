package project.e_buyankina.feature.operations.api.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class PageOperationsApi(

    @SerialName("page")
    val page: Int,

    @SerialName("operations")
    val operations: List<OperationApi>,

    @SerialName("has_next")
    val hasNext: Boolean,
)