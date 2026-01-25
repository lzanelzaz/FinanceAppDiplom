package project.e_buyankina.auth_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.e_buyankina.auth_api.domain.usecases.AuthorizeUseCase
import project.e_buyankina.auth_api.domain.usecases.CreateUserUseCase
import project.e_buyankina.common.navigation.features.MainNavigation
import project.e_buyankina.common_network.ServerException

internal class AuthViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val authorizeUseCase: AuthorizeUseCase,
    private val mainNavigation: MainNavigation,
) : ViewModel() {

    private val state = MutableStateFlow(State())
    val uiState: StateFlow<UiState> = state
        .map(::mapToUi)
        .stateIn(viewModelScope, SharingStarted.Eagerly, mapToUi(State()))

    private val newsChannel = Channel<News>(Channel.BUFFERED)
    val news = newsChannel.receiveAsFlow()

    fun onEmailTextUpdated(text: String) {
        state.update {
            it.copy(emailText = text, isEmailError = false)
        }
    }

    fun onPasswordTextUpdated(text: String) {
        state.update {
            it.copy(passwordText = text, isPasswordError = false)
        }
    }

    fun onNameTextUpdated(text: String) {
        state.update {
            it.copy(nameText = text, isNameError = false)
        }
    }

    fun onPasswordIconClick() {
        state.update {
            it.copy(isPasswordMasked = !it.isPasswordMasked)
        }
    }

    fun onPrimaryButtonClick() = with(state.value) {
        val validEmail = emailRegex.matches(emailText)
        val validPassword = passwordRegex.matches(passwordText)
        val validName = isHasAccount || nameText.isNotBlank()
        val validInput = validEmail && validPassword && validName
        if (!validInput) {
            state.update {
                it.copy(
                    isEmailError = !validEmail,
                    isPasswordError = !validPassword,
                    isNameError = !validName,
                )
            }
            return@with
        }
        state.update {
            it.copy(isLoading = true)
        }
        enterUser()
    }

    private fun enterUser() = with(state.value) {
        viewModelScope.launch {
            runCatching {
                if (isHasAccount) {
                    authorizeUseCase(
                        login = emailText,
                        password = passwordText,
                    )
                } else {
                    createUserUseCase(
                        login = emailText,
                        password = passwordText,
                        username = nameText,
                    )
                }
            }.onSuccess {
                newsChannel.send(News.OpenRoute(mainNavigation.mainRoute))
            }.onFailure { error ->
                newsChannel.send(News.ShowToast((error as? ServerException)?.message))
                state.update {
                    it.copy(isLoading = false)
                }
            }
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
            emailErrorTextRes = R.string.email_error.takeIf { isEmailError },
            passwordText = if (isPasswordMasked) MASK_CHAR.repeat(passwordText.length) else passwordText,
            passwordErrorTextRes = R.string.password_error.takeIf { isPasswordError },
            nameText = nameText,
            nameErrorTextRes = R.string.name_error.takeIf { isNameError },
            isNameFieldVisible = !isHasAccount,
            primaryButtonText = if (isHasAccount) R.string.log_in else R.string.registration,
            secondaryButtonText = if (isHasAccount) R.string.me_new_user else R.string.already_has_account,
            passwordIcon = if (isPasswordMasked) R.drawable.visibility_lock_24dp else R.drawable.visibility_off_24dp,
            isLoading = isLoading,
        )
    }

    private data class State(
        val isHasAccount: Boolean = true,
        val emailText: String = "",
        val isEmailError: Boolean = false,
        val passwordText: String = "",
        val isPasswordError: Boolean = false,
        val isPasswordMasked: Boolean = false,
        val nameText: String = "",
        val isNameError: Boolean = false,
        val isLoading: Boolean = false,
    )

    private companion object {

        const val MASK_CHAR = "*"

        val emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$".toRegex()
        val passwordRegex = "^(?=.*\\d).{5,}$".toRegex()
    }
}