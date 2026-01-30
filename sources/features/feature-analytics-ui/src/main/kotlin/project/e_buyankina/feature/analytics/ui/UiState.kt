package project.e_buyankina.feature.analytics.ui

import project.e_buyankina.feature.analytics.barchart.BarGroup
import java.math.BigDecimal

internal data class UiState(
    val dateRange: String,
    val chartTypes: List<ChartType>,
    val selectedChartType: ChartType,
    val pieChartData: Map<String, BigDecimal>,
    val barChartData: Map<String, BarGroup>,
    val startDateMillis: Long,
    val endDateMillis: Long,
)