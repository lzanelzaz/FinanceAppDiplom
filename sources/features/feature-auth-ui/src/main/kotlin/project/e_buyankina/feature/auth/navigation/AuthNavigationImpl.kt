package project.e_buyankina.feature.auth.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import project.e_buyankina.common.navigation.features.AuthNavigation
import project.e_buyankina.feature.auth.ui.AuthScreen

internal class AuthNavigationImpl : AuthNavigation {

    override val authRoute = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            AuthScreen(appNavController = navController)
        }
    }
}

private const val baseRoute = "authRoute"