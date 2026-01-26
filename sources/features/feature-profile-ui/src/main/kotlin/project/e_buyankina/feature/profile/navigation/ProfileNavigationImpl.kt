package project.e_buyankina.feature.profile.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import project.e_buyankina.common.navigation.features.ProfileNavigation
import project.e_buyankina.feature.profile.ui.ProfileScreen

internal class ProfileNavigationImpl : ProfileNavigation {

    override val profileRoute = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            ProfileScreen(modifier = modifier, appNavController = navController)
        }
    }
}

private const val baseRoute = "profileRoute"