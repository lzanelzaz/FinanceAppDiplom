package project.e_buyankina.auth_ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import project.e_buyankina.auth_ui.AuthScreen
import project.e_buyankina.common.navigation.features.AuthNavigation

internal class AuthNavigationImpl : AuthNavigation {

    override val authRoute = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            AuthScreen()
        }
    }
}

private const val baseRoute = "auth"