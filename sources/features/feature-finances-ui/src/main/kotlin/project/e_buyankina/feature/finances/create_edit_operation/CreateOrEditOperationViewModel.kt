package project.e_buyankina.feature.finances.create_edit_operation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.common.Expense
import project.e_buyankina.feature.finances.common.Income
import project.e_buyankina.feature.finances.common.Type
import project.e_buyankina.feature.operations.api.domain.usecases.CreateOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.DeleteOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.EditOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.GetOperationUseCase
import java.time.format.DateTimeFormatter

internal class CreateOrEditOperationViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getOperationUseCase: GetOperationUseCase,
    private val createOperationUseCase: CreateOperationUseCase,
    private val editOperationUseCase: EditOperationUseCase,
    private val deleteOperationUseCase: DeleteOperationUseCase,
) : ViewModel() {

    private var initState: State? = null

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<UiState> = state
        .map(::mapToUi)
        .stateIn(viewModelScope, SharingStarted.Eagerly, mapToUi(State()))

    fun load(operationId: String?, getString: (Int) -> String) {
        viewModelScope.launch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            if (operationId != null) {
                val operation = getOperationUseCase(operationId)
                requireNotNull(operation)
                val operationState = with(operation) {
                    State(
                        selectedDate = date,
                        amount = amount,
                        selectedType = Type.valueOf(type.serialName),
                        selectedSubtype = (Expense.entries + Income.entries).first { getString(it.text) == subtype }
                    )
                }
                initState = operationState
                state.update { operationState }
            }
        }
    }

    private fun mapToUi(state: State): UiState = with(state) {
        return@with UiState(
            selectedDate = selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            amount = amount.toString(),
            selectedType = selectedType,
            selectedSubtype = selectedSubtype,
            types = Type.entries,
            subtypes = when (selectedType) {
                Type.EXPENSE -> Expense.entries
                Type.INCOME -> Income.entries
            },
            keyboard = keyboard()
        )
    }

    private fun keyboard() = listOf(
        UiState.KeyBoardItem.Digit(1),
        UiState.KeyBoardItem.Digit(2),
        UiState.KeyBoardItem.Digit(3),
        UiState.KeyBoardItem.Digit(4),
        UiState.KeyBoardItem.Digit(5),
        UiState.KeyBoardItem.Digit(6),
        UiState.KeyBoardItem.Digit(7),
        UiState.KeyBoardItem.Digit(8),
        UiState.KeyBoardItem.Digit(9),
        UiState.KeyBoardItem.Point(","),
        UiState.KeyBoardItem.Digit(0),
        UiState.KeyBoardItem.Backspace(R.drawable.backspace_24dp),
    )
}