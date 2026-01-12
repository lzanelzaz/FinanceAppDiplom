package project.e_buyankina.auth

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class State(
    @param:StringRes val title: Int,
    val isNameFieldVisible: Boolean,
    @param:StringRes val primaryButtonText: Int,
    @param:StringRes val secondaryButtonText: Int,
    @param:DrawableRes val passwordIcon: Int,
    val isPrimaryButtonLoading: Boolean,
    val isSecondaryButtonLoading: Boolean,
)