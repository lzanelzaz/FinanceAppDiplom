package project.e_buyankina.feature.analytics.ui

internal data class UiState(
    val dateRange: String,
    val chartTypes: List<ChartType>,
    val selectedChartType: ChartType,
    val pieChartData: Map<String, Float>,
    val barChartData: Map<String, List<Float>>,
    val startDateMillis: Long,
    val endDateMillis: Long,
)