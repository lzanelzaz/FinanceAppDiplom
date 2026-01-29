package project.e_buyankina.feature.finances.common

import androidx.annotation.StringRes
import project.e_buyankina.feature.finances.R

internal enum class Type(
    @param:StringRes val text: Int,
    val subtypes: List<Subtype>
) {
    EXPENSE(R.string.expense, Subtype.Expense.entries),
    INCOME(R.string.income, Subtype.Income.entries)
}