package project.e_buyankina.feature.finances.create_edit_operation

import project.e_buyankina.feature.finances.common.Expense
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.common.Type
import java.math.BigDecimal
import java.time.LocalDate

internal data class State(
    val selectedDate: LocalDate = LocalDate.now(),
    val amount: BigDecimal = BigDecimal.ZERO,
    val selectedType: Type = Type.EXPENSE,
    val selectedSubtype: Subtype = Expense.entries.first(),
)