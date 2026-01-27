package project.e_buyankina.feature.finances.common

import androidx.annotation.StringRes
import project.e_buyankina.feature.finances.R

internal enum class Type(
    @param:StringRes val text: Int,
) {
    EXPENSE(R.string.expense),
    INCOME(R.string.income),
}