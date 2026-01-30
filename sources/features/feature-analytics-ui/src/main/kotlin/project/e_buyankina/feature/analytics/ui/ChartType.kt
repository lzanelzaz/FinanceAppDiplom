package project.e_buyankina.feature.analytics.ui

import androidx.annotation.StringRes
import project.e_buyankina.feature.analytics.R

internal enum class ChartType(@field:StringRes val text: Int) {

    BY_CATEGORIES(R.string.categories),
    BY_BALANCE(R.string.balance)
}