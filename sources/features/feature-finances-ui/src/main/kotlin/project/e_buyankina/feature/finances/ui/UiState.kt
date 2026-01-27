package project.e_buyankina.feature.finances.ui

import project.e_buyankina.feature.finances.common.Subtype

internal data class UiState(
    val operations: List<UiOperation> = emptyList()
) {

    data class UiOperation(
        val operationId: String,
        val amount: String,
        val date: String,
        val subtype: Subtype,
        val description: String?,
    )
}