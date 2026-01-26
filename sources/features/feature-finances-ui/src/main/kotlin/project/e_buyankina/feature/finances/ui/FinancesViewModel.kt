package project.e_buyankina.feature.finances.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.SubscribeToOperationsUseCase

internal class FinancesViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val subscribeToOperationsUseCase: SubscribeToOperationsUseCase,
) : ViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<State> = state.asStateFlow()

    init {
        viewModelScope.launch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            subscribeToOperationsUseCase(accountId)
                .onEach { operations ->
                    state.update { it.copy(operations = operations) }
                }
                .launchIn(this)
        }
    }
}