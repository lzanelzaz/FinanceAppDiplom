package project.e_buyankina.auth_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class AuthViewModel() : ViewModel() {

    private val state = MutableStateFlow(initState())
    val uiState: StateFlow<UiState> = state
        .map(::mapToUi)
        .stateIn(viewModelScope, SharingStarted.Eagerly, mapToUi(initState()))

    private fun initState() = State(
        isHasAccount = true,
        emailText = "",
        passwordText = "",
        isPasswordMasked = false,
        nameText = "",
        isLoading = false,
    )

    fun onEmailTextUpdated(text: String) {
        state.update {
            it.copy(emailText = text)
        }
    }

    fun onPasswordTextUpdated(text: String) {
        state.update {
            it.copy(passwordText = text)
        }
    }

    fun onNameTextUpdated(text: String) {
        state.update {
            it.copy(nameText = text)
        }
    }

    fun onPasswordIconClick() {
        state.update {
            it.copy(isPasswordMasked = !it.isPasswordMasked)
        }
    }

    fun onPrimaryButtonClick() {
        val validInput = with(state.value) {
            emailRegex.matches(emailText) &&
                    passwordRegex.matches(passwordText) &&
                    (isHasAccount || nameText.isNotBlank())
        }
        if (validInput) {
            state.update {
                it.copy(isLoading = true)
            }

        } else {

        }
    }

    fun onSecondaryButtonClick() {
        state.update {
            it.copy(isHasAccount = !it.isHasAccount)
        }
    }

    private fun mapToUi(state: State): UiState = with(state) {
        UiState(
            title = if (isHasAccount) R.string.log_into_account else R.string.registration,
            emailText = emailText,
            passwordText = if (isPasswordMasked) MASK_CHAR.repeat(passwordText.length) else passwordText,
            nameText = nameText,
            isNameFieldVisible = !isHasAccount,
            primaryButtonText = if (isHasAccount) R.string.log_in else R.string.registration,
            secondaryButtonText = if (isHasAccount) R.string.me_new_user else R.string.already_has_account,
            passwordIcon = if (isPasswordMasked) R.drawable.visibility_lock_24dp else R.drawable.visibility_off_24dp,
            isLoading = isLoading,
        )
    }

    private data class State(
        val isHasAccount: Boolean,
        val emailText: String,
        val passwordText: String,
        val isPasswordMasked: Boolean,
        val nameText: String,
        val isLoading: Boolean,
    )

    private companion object {

        const val MASK_CHAR = "*"

        val emailRegex = "^[\\\\w.%+-]+@[\\\\w.-]+\\\\.[A-Za-z]{2,}$".toRegex()
        val passwordRegex = "^(?=.*\\d).{5,}$".toRegex()
    }
}