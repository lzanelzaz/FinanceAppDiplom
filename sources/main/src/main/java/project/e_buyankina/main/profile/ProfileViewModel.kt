package project.e_buyankina.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.e_buyankina.auth_api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.auth_api.domain.usecases.LogOutUseCase
import project.e_buyankina.common.navigation.features.AuthNavigation

internal class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val authNavigation: AuthNavigation,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val newsChannel = Channel<News>(Channel.BUFFERED)
    val news = newsChannel.receiveAsFlow()


    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    userName = getCurrentUserUseCase()?.username.orEmpty()
                )
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            logOutUseCase()
            newsChannel.send(News.OpenRoute(authNavigation.authRoute))
        }
    }
}