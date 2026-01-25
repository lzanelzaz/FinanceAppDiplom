package project.e_buyankina.splashscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import project.e_buyankina.common_ui.theme.AppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val viewModel = koinViewModel<SplashViewModel>()
                LaunchedEffect(Unit) {
                    viewModel.news.collectLatest { news ->
                        when (news) {

                            else -> {
                                splashScreen.setKeepOnScreenCondition { false }
                                //AuthScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
