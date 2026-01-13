package project.e_buyankina.splashscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import project.e_buyankina.auth.AuthScreen
import project.e_buyankina.common_ui.theme.AppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition { false }

        setContent {
            AppTheme {
                AuthScreen()
            }
        }
    }
}
