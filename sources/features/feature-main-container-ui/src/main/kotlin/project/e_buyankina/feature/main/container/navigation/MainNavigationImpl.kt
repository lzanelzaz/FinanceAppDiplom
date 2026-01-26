package project.e_buyankina.feature.main.container.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import project.e_buyankina.common.navigation.features.MainNavigation
import project.e_buyankina.feature.main.container.ui.MainScreen

internal class MainNavigationImpl : MainNavigation {

    override val mainRoute = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            MainScreen(appNavController = navController)
        }
    }
}

private const val baseRoute = "main"