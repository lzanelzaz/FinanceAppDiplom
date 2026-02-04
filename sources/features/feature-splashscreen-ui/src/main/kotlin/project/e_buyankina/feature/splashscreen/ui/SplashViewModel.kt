package project.e_buyankina.feature.splashscreen.ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import project.e_buyankina.common.error.BaseViewModel
import project.e_buyankina.common.error.ErrorHandler
import project.e_buyankina.common.error.safeLaunch
import project.e_buyankina.common.navigation.features.AuthNavigation
import project.e_buyankina.common.navigation.features.MainNavigation
import project.e_buyankina.feature.auth.api.domain.usecases.GetCurrentUserUseCase

internal class SplashViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    val authNavigation: AuthNavigation,
    val mainNavigation: MainNavigation,
    override val errorHandler: ErrorHandler,
) : BaseViewModel() {

    private val newsChannel = Channel<News>(Channel.BUFFERED)
    val news = newsChannel.receiveAsFlow()

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        safeLaunch {
            val user = getCurrentUserUseCase()
            val route = if (user == null) authNavigation.authRoute else mainNavigation.mainRoute
            newsChannel.send(News.OpenRoute(route))
        }
    }
}