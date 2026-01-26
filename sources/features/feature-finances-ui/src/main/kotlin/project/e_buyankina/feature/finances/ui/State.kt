package project.e_buyankina.feature.finances.ui

import project.e_buyankina.feature.operations.api.domain.Operation

internal data class State(
    val operations: List<Operation> = emptyList()
)