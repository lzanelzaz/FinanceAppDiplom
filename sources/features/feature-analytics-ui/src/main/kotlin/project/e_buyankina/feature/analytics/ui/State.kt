package project.e_buyankina.feature.analytics.ui

import org.joda.time.DateTime
import project.e_buyankina.feature.operations.api.domain.Operation

internal data class State(
    val startDate: DateTime = DateTime(System.currentTimeMillis()),
    val endDate: DateTime = startDate.minusMonths(1),
    val operations: List<Operation> = emptyList(),
)