package project.e_buyankina.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.koin.compose.getKoin
import project.e_buyankina.common.navigation.features.AuthNavigation
import project.e_buyankina.common.navigation.features.MainNavigation
import project.e_buyankina.common.navigation.features.SplashNavigation

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val splashNavigation = getKoin().get<SplashNavigation>()
    val authNavigation = getKoin().get<AuthNavigation>()
    val mainNavigation = getKoin().get<MainNavigation>()
    NavHost(
        navController = navController,
        startDestination = splashNavigation.splashRoute
    ) {
        register(
            splashNavigation,
            navController = navController,
            modifier = modifier
        )
        register(
            authNavigation,
            navController = navController,
            modifier = modifier
        )
        register(
            mainNavigation,
            navController = navController,
            modifier = modifier
        )
    }
}