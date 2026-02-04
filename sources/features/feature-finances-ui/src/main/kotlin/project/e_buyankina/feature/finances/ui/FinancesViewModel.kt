package project.e_buyankina.feature.finances.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joda.time.format.DateTimeFormat
import project.e_buyankina.common.error.BaseViewModel
import project.e_buyankina.common.error.ErrorHandler
import project.e_buyankina.common.error.safeLaunch
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.common.Subtype.Companion.findByCode
import project.e_buyankina.feature.finances.ui.UiState.UiOperation
import project.e_buyankina.feature.operations.api.domain.Operation
import project.e_buyankina.feature.operations.api.domain.usecases.SubscribeToOperationsUseCase
import java.util.Locale

internal class FinancesViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val subscribeToOperationsUseCase: SubscribeToOperationsUseCase,
    override val errorHandler: ErrorHandler,
) : BaseViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<UiState> = state
        .map(::mapToUi)
        .stateIn(viewModelScope, SharingStarted.Eagerly, mapToUi(State()))

    init {
        observeOperations()
    }

    private fun observeOperations() {
        safeLaunch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            subscribeToOperationsUseCase(accountId)
                .catch { emit(emptyList()) }
                .onEach { operations -> state.update { it.copy(operations = operations) } }
                .launchIn(this)
        }
    }

    private fun mapToUi(state: State): UiState = with(state) {
        val dateFormat = DateTimeFormat.forPattern("dd MMMM yyyy").withLocale(Locale("ru"))
        UiState(
            operationsGrouped = operations.groupBy { it.date }.map { (date, operations) ->
                UiState.OperationsGrouped(date.toString(dateFormat), operations.map { it.toUi() })
            }
        )
    }

    private data class State(
        val operations: List<Operation> = emptyList()
    )

    private fun Operation.toUi() = UiOperation(
        operationId = operationId,
        amount = if (amount.signum() == 1) "+$amount ₽" else "$amount ₽",
        subtype = Subtype.all.findByCode(subtype)!!,
        description = description,
        isAmountColorPositive = amount.signum() == 1,
    )
}