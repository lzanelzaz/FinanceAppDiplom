package project.e_buyankina.splashscreen

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SplashScreen(
    onOpenAuthScreen: () -> Unit,
    onOpenMainScreen: () -> Unit,
) {
    val viewModel = koinViewModel<SplashViewModel>()
    val activity = LocalActivity.current as SplashActivity
    LaunchedEffect(Unit) {
        viewModel.news.collectLatest { news ->
            when (news) {
                is News.OpenAuthScreen -> onOpenAuthScreen()
                is News.OpenMainScreen -> onOpenMainScreen()
            }
            activity.finishSplashScreen()
        }
    }
}