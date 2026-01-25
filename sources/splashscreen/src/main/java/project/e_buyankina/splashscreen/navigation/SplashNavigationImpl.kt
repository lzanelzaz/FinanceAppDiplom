package project.e_buyankina.splashscreen.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import project.e_buyankina.common.navigation.features.SplashNavigation
import project.e_buyankina.splashscreen.SplashScreen

internal class SplashNavigationImpl : SplashNavigation {

    override val splashRoute = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            SplashScreen(appNavController = navController)
        }
    }
}

private const val baseRoute = "splash"