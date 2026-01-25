package project.e_buyankina.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import project.e_buyankina.auth_api.domain.usecases.GetCurrentUserUseCase

internal class SplashViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {

    private val newsChannel = Channel<News>(Channel.BUFFERED)
    val news = newsChannel.receiveAsFlow()

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val user = getCurrentUserUseCase()
            if (user == null) {
                newsChannel.send(News.OpenAuthScreen)
            } else {
                newsChannel.send(News.OpenMainScreen)
            }
        }
    }
}