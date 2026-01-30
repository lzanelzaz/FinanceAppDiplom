package project.e_buyankina.feature.analytics.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import project.e_buyankina.common.navigation.features.AnalyticsNavigation
import project.e_buyankina.feature.analytics.ui.AnalyticsScreen

internal class AnalyticsNavigationImpl : AnalyticsNavigation {

    override val analyticsRoute = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            AnalyticsScreen(modifier)
        }
    }
}

private const val baseRoute = "analyticsRoute"