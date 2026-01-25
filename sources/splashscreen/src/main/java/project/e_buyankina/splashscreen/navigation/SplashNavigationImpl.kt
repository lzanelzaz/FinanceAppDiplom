package project.e_buyankina.splashscreen.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.compose.getKoin
import project.e_buyankina.common.navigation.features.AuthNavigation
import project.e_buyankina.common.navigation.features.MainNavigation
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
            val authNavigation = getKoin().get<AuthNavigation>()
            val mainNavigation = getKoin().get<MainNavigation>()
            SplashScreen(
                onOpenAuthScreen = { navController.navigate(authNavigation.authRoute) },
                onOpenMainScreen = { navController.navigate(mainNavigation.mainRoute) },
            )
        }
    }
}

private const val baseRoute = "splash"