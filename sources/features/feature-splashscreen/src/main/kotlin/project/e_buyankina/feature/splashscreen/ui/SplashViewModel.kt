package project.e_buyankina.feature.splashscreen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import project.e_buyankina.common.navigation.features.AuthNavigation
import project.e_buyankina.common.navigation.features.MainNavigation
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase

internal class SplashViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    val authNavigation: AuthNavigation,
    val mainNavigation: MainNavigation,
) : ViewModel() {

    private val newsChannel = Channel<News>(Channel.BUFFERED)
    val news = newsChannel.receiveAsFlow()

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val user = getCurrentUserUseCase()
            val route = if (user == null) authNavigation.authRoute else mainNavigation.mainRoute
            newsChannel.send(News.OpenRoute(route))
        }
    }
}