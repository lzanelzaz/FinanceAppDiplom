package project.e_buyankina.feature.splashscreen.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import project.e_buyankina.common.error.HandleContentError
import project.e_buyankina.common.navigation.AppNavGraph
import project.e_buyankina.common.ui.theme.AppTheme

class SplashActivity : ComponentActivity() {

    private var showSplashScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition { showSplashScreen }
        setContent {
            AppTheme {
                HandleContentError { contentPadding ->
                    val navController = rememberNavController()
                    AppNavGraph(
                        modifier = Modifier.padding(contentPadding),
                        navController = navController,
                    )
                }

            }
        }
    }

    fun finishSplashScreen() {
        lifecycleScope.launch {
            delay(50)
            showSplashScreen = false
        }
    }
}