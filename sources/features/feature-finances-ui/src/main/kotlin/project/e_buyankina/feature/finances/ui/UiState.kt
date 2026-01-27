package project.e_buyankina.feature.finances.ui

import androidx.annotation.DrawableRes

internal data class UiState(
    val operations: List<UiOperation> = emptyList()
) {

    data class UiOperation(
        val operationId: String,
        @param:DrawableRes val icon: Int,
        val amount: String,
        val date: String,
        val subtype: String,
        val description: String?,
    )
}