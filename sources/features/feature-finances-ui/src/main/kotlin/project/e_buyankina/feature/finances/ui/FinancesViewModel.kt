package project.e_buyankina.feature.finances.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.ui.UiState.UiOperation
import project.e_buyankina.feature.operations.api.domain.Operation
import project.e_buyankina.feature.operations.api.domain.usecases.SubscribeToOperationsUseCase

internal class FinancesViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val subscribeToOperationsUseCase: SubscribeToOperationsUseCase,
) : ViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<UiState> = state
        .map(::mapToUi)
        .stateIn(viewModelScope, SharingStarted.Eagerly, mapToUi(State()))

    private val newsChannel = Channel<News>(Channel.BUFFERED)
    val news = newsChannel.receiveAsFlow()

    init {
        observeOperations()
    }

    private fun observeOperations() {
        viewModelScope.launch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            subscribeToOperationsUseCase(accountId)
                .catch { emit(emptyList()) }
                .onEach { operations -> state.update { it.copy(operations = operations) } }
                .launchIn(this)
        }
    }

    private fun mapToUi(state: State): UiState = with(state) {
        UiState(
            operationsGrouped = operations.groupBy { it.date }.map { (date, operations) ->
                UiState.OperationsGrouped(date.toString(), operations.map { it.toUi() }
                )
            }
        )
    }

    private data class State(
        val operations: List<Operation> = emptyList()
    )

    private fun Operation.toUi() = UiOperation(
        operationId = operationId,
        amount = amount.toString(),
        subtype = Subtype.findByCode(subtype),
        description = description,
    )
}