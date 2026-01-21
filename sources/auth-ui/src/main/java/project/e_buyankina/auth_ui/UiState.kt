package project.e_buyankina.auth_ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class UiState(
    @param:StringRes val title: Int,
    val emailText: String,
    val passwordText: String,
    val nameText: String,
    val isNameFieldVisible: Boolean,
    @param:StringRes val primaryButtonText: Int,
    @param:StringRes val secondaryButtonText: Int,
    @param:DrawableRes val passwordIcon: Int,
    val isLoading: Boolean,
)