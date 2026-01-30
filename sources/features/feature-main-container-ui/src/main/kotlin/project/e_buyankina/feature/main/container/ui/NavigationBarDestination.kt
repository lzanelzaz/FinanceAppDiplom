package project.e_buyankina.feature.main.container.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import project.e_buyankina.feature.main.container.R

internal enum class NavigationBarDestination(
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int,
) {
    FINANCES(R.string.finances, R.drawable.account_balance_24dp),
    ANALYTICS(R.string.analytics, R.drawable.ecg_24dp),
    PROFILE(R.string.profile, R.drawable.account_circle_24dp)
}