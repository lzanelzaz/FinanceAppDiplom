package project.e_buyankina.feature.finances.create_edit_operation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.common.Subtype.Companion.findByCode
import project.e_buyankina.feature.finances.common.Type
import project.e_buyankina.feature.operations.api.domain.NewOperation
import project.e_buyankina.feature.operations.api.domain.Operation
import project.e_buyankina.feature.operations.api.domain.TransactionType
import project.e_buyankina.feature.operations.api.domain.usecases.CreateOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.DeleteOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.EditOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.GetOperationUseCase
import java.math.BigDecimal
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

    private val newsChannel = Channel<News>(Channel.BUFFERED)
    val news = newsChannel.receiveAsFlow()

    fun load(operationId: String?) {
        viewModelScope.launch {
            val accountId = getCurrentUserUseCase()?.accountId
            requireNotNull(accountId)
            val loadedState = if (operationId != null) {
                val operation = getOperationUseCase(operationId)
                with(requireNotNull(operation)) {
                    val type = when (type) {
                        TransactionType.EXPENSE -> Type.EXPENSE
                        TransactionType.INCOME -> Type.INCOME
                    }
                    State(
                        accountId = accountId,
                        operationId = operationId,
                        selectedType = type,
                        selectedSubtype = type.subtypes.findByCode(subtype) ?: type.subtypes.first(),
                        selectedDate = date,
                        amount = amount.run { if (type == Type.EXPENSE) this.negate() else this },
                        details = description,
                    ).also { initState = it }
                }
            } else {
                State(accountId = accountId)
            }
            state.update { loadedState }
        }
    }

    fun onTypeChanged(newType: Type) = state.update { it.copy(selectedType = newType) }

    fun onSubtypeChanged(newSubtype: Subtype) = state.update { it.copy(selectedSubtype = newSubtype) }

    fun onDateChanged(millis: Long?) = millis?.let { state.update { it.copy(selectedDate = DateTime(millis)) } }

    fun onDetailsChanged(text: String) = state.update { it.copy(details = text) }

    fun onKeyClicked(key: UiState.KeyBoardItem) {
        val amount = state.value.amount.toString()
        val (floor, fraction) = state.value.amount.setScale(2).toString().split(".")
        when (key) {
            is UiState.KeyBoardItem.Backspace -> {
                state.update { it.copy(amount = if (amount.length == 1) BigDecimal.ZERO else BigDecimal(amount.dropLast(1))) }
            }

            is UiState.KeyBoardItem.Digit -> {
                val parsedAmount = if (amount.contains(".") && fraction == "00") {
                    amount.dropLast(1)
                } else if (amount.contains(".") && fraction.last() != '0') {
                    return
                } else {
                    amount
                }
                state.update { it.copy(amount = BigDecimal("$parsedAmount${key.digit}")) }
            }

            is UiState.KeyBoardItem.Point -> if (fraction == "00") state.update { it.copy(amount = BigDecimal("${it.amount}.0")) }
        }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            val state = state.value
            if (initState == null) {
                val newOperation = NewOperation(
                    type = when (state.selectedType) {
                        Type.EXPENSE -> TransactionType.EXPENSE
                        Type.INCOME -> TransactionType.INCOME
                    },
                    subtype = state.selectedSubtype.code,
                    amount = state.amount.run { if (state.selectedType == Type.EXPENSE) this.negate() else this },
                    date = state.selectedDate,
                    description = state.details,
                )
                createOperationUseCase(
                    accountId = state.accountId!!,
                    newOperation = newOperation,
                )
            } else if (state != initState) {
                val operation = Operation(
                    operationId = state.operationId!!,
                    type = when (state.selectedType) {
                        Type.EXPENSE -> TransactionType.EXPENSE
                        Type.INCOME -> TransactionType.INCOME
                    },
                    subtype = state.selectedSubtype.code,
                    amount = state.amount.run { if (state.selectedType == Type.EXPENSE) this.negate() else this },
                    date = state.selectedDate,
                    description = state.details,
                )
                editOperationUseCase(
                    accountId = state.accountId!!,
                    operation = operation,
                )
            }
            newsChannel.send(News.Close)
        }
    }

    fun onDeleteClick() {
        viewModelScope.launch {
            if (initState != null) {
                val state = state.value
                deleteOperationUseCase(
                    accountId = state.accountId!!,
                    operationId = state.operationId!!,
                )
            }
            newsChannel.send(News.Close)
        }
    }

    private fun mapToUi(state: State): UiState = with(state) {
        val dateFormat = DateTimeFormat.forPattern("dd MMMM yyyy").withLocale(Locale("ru"))
        return@with UiState(
            selectedType = selectedType,
            selectedSubtype = selectedType.subtypes.findByCode(selectedSubtype.code) ?: selectedType.subtypes.first(),
            types = Type.entries,
            subtypes = selectedType.subtypes,
            selectedDate = selectedDate.toString(dateFormat),
            selectedDateMillis = selectedDate.millis,
            amount = "$amount ₽",
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