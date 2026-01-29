package project.e_buyankina.feature.finances.create_edit_operation

import org.joda.time.DateTime
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.common.Type
import java.math.BigDecimal

internal data class State(
    val accountId: String? = null,
    val operationId: String? = null,
    val selectedDate: DateTime = DateTime(System.currentTimeMillis()),
    val amount: BigDecimal = BigDecimal.ONE,
    val selectedType: Type = Type.EXPENSE,
    val selectedSubtype: Subtype = Subtype.Expense.entries.first(),
    val details: String? = null,
)