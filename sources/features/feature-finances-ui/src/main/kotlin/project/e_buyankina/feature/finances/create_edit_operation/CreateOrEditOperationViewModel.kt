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
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.common.Type
import project.e_buyankina.feature.operations.api.domain.TransactionType
import project.e_buyankina.feature.operations.api.domain.usecases.CreateOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.DeleteOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.EditOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.GetOperationUseCase
import java.util.Locale

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

    fun load(operationId: String?) {
        viewModelScope.launch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            if (operationId != null) {
                val operation = getOperationUseCase(operationId)
                requireNotNull(operation)
                val operationState = with(operation) {
                    State(
                        selectedType = when (type) {
                            TransactionType.EXPENSE -> Type.EXPENSE
                            TransactionType.INCOME -> Type.INCOME
                        },
                        selectedSubtype = Subtype.findByCode(subtype),
                        selectedDate = date,
                        amount = amount,
                        details = description,
                    )
                }
                initState = operationState
                state.update { operationState }
            }
        }
    }

    fun onTypeChanged(newType: Type) {
        state.update { it.copy(selectedType = newType) }
    }

    fun onSubtypeChanged(newSubtype: Subtype) {
        state.update { it.copy(selectedSubtype = newSubtype) }
    }

    fun onDateChanged(millis: Long?) {
        millis ?: return
        state.update { it.copy(selectedDate = DateTime(millis)) }
    }

    fun onKeyClicked(key: UiState.KeyBoardItem) {

    }

    fun onSaveClick() {

    }

    fun onDeleteClick() {

    }

    private fun mapToUi(state: State): UiState = with(state) {
        val dateFormat = DateTimeFormat.forPattern("dd MMMM yyyy").withLocale(Locale("ru"))
        return@with UiState(
            selectedType = selectedType,
            selectedSubtype = selectedSubtype,
            types = Type.entries,
            subtypes = when (selectedType) {
                Type.EXPENSE -> Subtype.Expense.entries
                Type.INCOME -> Subtype.Income.entries
            },
            selectedDate = selectedDate.toString(dateFormat),
            selectedDateMillis = selectedDate.millis,
            amount = amount.toString(),
            details = details,
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