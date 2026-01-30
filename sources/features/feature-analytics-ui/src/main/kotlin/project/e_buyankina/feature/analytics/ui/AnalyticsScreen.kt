package project.e_buyankina.feature.analytics.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import project.e_buyankina.common.ui.emptyscreen.EmptyScreen
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.common.ui.theme.AppTheme
import project.e_buyankina.feature.analytics.R
import project.e_buyankina.feature.analytics.barchart.BarChart
import project.e_buyankina.feature.analytics.piechart.PieChart
import project.e_buyankina.common.ui.R as CommonR

@Composable
internal fun AnalyticsScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<AnalyticsViewModel>()
    val state = viewModel.uiState.collectAsState()

    AnalyticsContent(
        modifier.fillMaxSize(),
        state.value,
        onDateRangeSelected = viewModel::onDateRangeSelected,
        onChartTypeChanged = viewModel::onChartTypeChanged,
    )
}

@Composable
internal fun AnalyticsContent(
    modifier: Modifier = Modifier,
    state: UiState,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit = {},
    onChartTypeChanged: (ChartType) -> Unit = {},
) {
    var showDatePicker by remember { mutableStateOf(false) }
    Column(modifier) {
        TypeBlock(
            state,
            isSelected = { state.selectedChartType == it },
            onChartTypeChanged
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp), onClick = { showDatePicker = true }) {
            Text(state.dateRange)
        }
        if (state.selectedChartType == ChartType.BY_CATEGORIES && state.pieChartData.isEmpty() ||
            state.selectedChartType == ChartType.BY_BALANCE && state.barChartData.isEmpty()
        ) {
            EmptyScreen(modifier, R.drawable.cat_chart)
        } else {
            when (state.selectedChartType) {
                ChartType.BY_CATEGORIES -> PieChart(
                    chartData = state.pieChartData,
                    modifier = Modifier.fillMaxSize()
                )
                ChartType.BY_BALANCE -> BarChart(
                    chartData = state.barChartData,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
    }

    if (showDatePicker) {
        DateRangePickerModal(
            state = state,
            onDateRangeSelected = onDateRangeSelected,
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TypeBlock(
    state: UiState,
    isSelected: (ChartType) -> Boolean,
    selectedChanged: (ChartType) -> Unit,
) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
    ) {
        state.chartTypes.forEachIndexed { index, type ->
            ToggleButton(
                checked = isSelected(type),
                onCheckedChange = { selectedChanged(type) },
                modifier = Modifier.weight(1f),
                shapes = when (index) {
                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    else -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                },
            ) {
                if (isSelected(type)) {
                    Icon(
                        painterResource(CommonR.drawable.check_24dp),
                        contentDescription = null,
                    )
                    Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                }
                Text(stringResource(type.text))
            }
        }
    }
}

@DayNightPreviews
@Composable
private fun Preview() {
    val state = UiState(
        startDateMillis = 0L,
        endDateMillis = 0L,
        chartTypes = ChartType.entries,
        selectedChartType = ChartType.entries.first(),
        dateRange = "10 октября 2025 - 10 ноября 2025",
        pieChartData = emptyMap(),
        barChartData = emptyMap(),
    )
    AppTheme {
        AnalyticsContent(
            state = state
        )
    }
}