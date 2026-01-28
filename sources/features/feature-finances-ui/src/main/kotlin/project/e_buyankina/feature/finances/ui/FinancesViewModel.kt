package project.e_buyankina.feature.finances.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.finances.common.Expense
import project.e_buyankina.feature.finances.ui.UiState.UiOperation
import project.e_buyankina.feature.operations.api.domain.Operation
import project.e_buyankina.feature.operations.api.domain.usecases.SubscribeToOperationsUseCase

internal class FinancesViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val subscribeToOperationsUseCase: SubscribeToOperationsUseCase,
) : ViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<UiState> = MutableStateFlow(initState())
//        state
//        .map(::mapToUi)
//        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState()))

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

    private data class State(
        val operations: List<Operation> = emptyList()
    )

    private fun initState() = UiState(
        listOf(
            UiState.OperationsGrouped(
                "11.11.2025",
                listOf(
                    UiOperation(
                        operationId = "",
                        amount = "+1 000 000 ₽",
                        date = "27.01.2026",
                        subtype = Expense.ENTERTAINMENT,
                        description = "Описание wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"
                    )
                )
            )
        )
    )
}