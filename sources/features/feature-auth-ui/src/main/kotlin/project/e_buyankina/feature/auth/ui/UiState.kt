package project.e_buyankina.feature.auth.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class UiState(
    @param:StringRes val title: Int,
    val emailText: String,
    @param:StringRes val emailErrorTextRes: Int?,
    val passwordText: String,
    @param:StringRes val passwordErrorTextRes: Int?,
    val nameText: String,
    @param:StringRes val nameErrorTextRes: Int?,
    val isNameFieldVisible: Boolean,
    @param:StringRes val primaryButtonText: Int,
    @param:StringRes val secondaryButtonText: Int,
    @param:DrawableRes val passwordIcon: Int,
    val isLoading: Boolean,
)