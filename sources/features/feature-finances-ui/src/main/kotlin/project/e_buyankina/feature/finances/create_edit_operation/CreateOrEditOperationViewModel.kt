package project.e_buyankina.feature.finances.create_edit_operation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.common.Expense
import project.e_buyankina.feature.finances.common.Type
import project.e_buyankina.feature.operations.api.domain.Operation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class CreateOrEditOperationViewModel(

) : ViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<UiState> = MutableStateFlow(initUi())
//        state
//        .map(::mapToUi)
//        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState()))

    private data class State(
        val operations: List<Operation> = emptyList()
    )

    private fun initUi() = UiState(
        selectedDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
        selectedType = Type.EXPENSE,
        selectedSubtype = Expense.ENTERTAINMENT,
        types = Type.entries,
        subtypes = Expense.entries,
        amount = "1 000 ₽",
        keyboard = listOf(
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
        ),
    )
}