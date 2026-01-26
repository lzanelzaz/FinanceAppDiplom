package project.e_buyankina.feature.splashscreen.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SplashScreen(
    appNavController: NavHostController,
) {
    val viewModel = koinViewModel<SplashViewModel>()
    val activity = LocalActivity.current as SplashActivity
    LaunchedEffect(Unit) {
        viewModel.news.collectLatest { news ->
            when (news) {
                is News.OpenRoute -> appNavController.navigate(news.route)
            }
            activity.finishSplashScreen()
        }
    }
}