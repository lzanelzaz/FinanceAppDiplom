package project.e_buyankina.feature.analytics.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.SubscribeToOperationsUseCase

internal class AnalyticsViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val subscribeToOperationsUseCase: SubscribeToOperationsUseCase,
) : ViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<State> = state

    init {
        loadOperations()
    }

    fun onDateRangeSelected(range: Pair<Long?, Long?>) {

    }

    private fun loadOperations() {
        viewModelScope.launch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            subscribeToOperationsUseCase(accountId)
                .catch { emit(emptyList()) }
                .onEach { operations -> state.update { it.copy(operations = operations) } }
                .first()
        }
    }

}