package project.e_buyankina.feature.analytics.ui

import org.joda.time.DateTime
import project.e_buyankina.feature.operations.api.domain.Operation

internal data class State(
    val endDate: DateTime = DateTime(System.currentTimeMillis()),
    val startDate: DateTime = endDate.minusMonths(1),
    val operations: List<Operation> = emptyList(),
    val selectedChartType: ChartType = ChartType.entries.first(),
)