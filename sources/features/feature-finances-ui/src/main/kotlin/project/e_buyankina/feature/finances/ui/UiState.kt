package project.e_buyankina.feature.finances.ui

import project.e_buyankina.feature.finances.common.Subtype

internal data class UiState(
    val operationsGrouped: List<OperationsGrouped>,
    val isLoading: Boolean,
) {

    data class OperationsGrouped(
        val date: String,
        val operations: List<UiOperation>
    )

    data class UiOperation(
        val operationId: String,
        val amount: String,
        val isAmountColorPositive: Boolean,
        val subtype: Subtype,
        val description: String?,
    )
}