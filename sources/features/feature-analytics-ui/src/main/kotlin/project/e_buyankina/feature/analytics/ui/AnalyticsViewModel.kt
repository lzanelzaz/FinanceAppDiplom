package project.e_buyankina.feature.analytics.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import project.e_buyankina.common.error.BaseViewModel
import project.e_buyankina.common.error.ErrorHandler
import project.e_buyankina.common.error.safeLaunch
import project.e_buyankina.feature.analytics.barchart.BarGroup
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.operations.api.domain.TransactionType
import project.e_buyankina.feature.operations.api.domain.usecases.GetOperationsPeriodUseCase
import java.util.Locale

internal class AnalyticsViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getOperationsPeriodUseCase: GetOperationsPeriodUseCase,
    override val errorHandler: ErrorHandler,
) : BaseViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<UiState> = state
        .map(::mapToUi)
        .stateIn(viewModelScope, SharingStarted.Eagerly, mapToUi(State()))

    init {
        loadOperations()
    }

    fun onDateRangeSelected(range: Pair<Long?, Long?>) {
        state.update { it.copy(startDate = DateTime(range.first), endDate = DateTime(range.second)) }
        loadOperations()
    }

    fun onChartTypeChanged(type: ChartType) = state.update { it.copy(selectedChartType = type) }

    private fun loadOperations() {
        val startDate = state.value.startDate
        val endDate = state.value.endDate
        safeLaunch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            val operations = getOperationsPeriodUseCase(accountId = accountId, startDate = startDate, endDate = endDate)
            state.update {
                it.copy(operations = operations)
            }
        }
    }

    private fun mapToUi(state: State): UiState = with(state) {
        val dateFormat = DateTimeFormat.forPattern("dd MMMM yyyy").withLocale(Locale("ru"))
        return@with UiState(
            startDateMillis = startDate.millis,
            endDateMillis = endDate.millis,
            chartTypes = ChartType.entries,
            selectedChartType = selectedChartType,
            dateRange = "${startDate.toString(dateFormat)} - ${endDate.toString(dateFormat)}",
            pieChartData = operations
                .filter { it.type == TransactionType.EXPENSE }
                .groupBy { it.subtype }
                .mapValues { (_, operations) -> operations.sumOf { it.amount.abs() } },
            barChartData = operations
                .groupBy { it.date.toString("MMM", Locale("ru")) }
                .mapValues { (_, monthOperations) ->
                    val incomeOperations = monthOperations.filter { it.type == TransactionType.INCOME }
                    val expenseOperations = monthOperations.filter { it.type == TransactionType.EXPENSE }
                    BarGroup(
                        income = incomeOperations.sumOf { it.amount.abs() },
                        expense = expenseOperations.sumOf { it.amount.abs() }
                    )
                },
        )
    }
}