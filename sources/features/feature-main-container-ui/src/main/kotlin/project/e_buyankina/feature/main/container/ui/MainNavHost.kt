package project.e_buyankina.feature.main.container.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.koin.compose.getKoin
import project.e_buyankina.common.navigation.features.AnalyticsNavigation
import project.e_buyankina.common.navigation.features.FinancesNavigation
import project.e_buyankina.common.navigation.features.ProfileNavigation
import project.e_buyankina.common.navigation.register

@Composable
internal fun MainNavHost(
    nestedNavController: NavHostController,
    startDestination: NavigationBarDestination,
    appNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val financesNavigation = getKoin().get<FinancesNavigation>()
    val profileNavigation = getKoin().get<ProfileNavigation>()
    val analyticsNavigation = getKoin().get<AnalyticsNavigation>()
    NavHost(
        navController = nestedNavController,
        startDestination = startDestination.route()
    ) {
        register(
            financesNavigation,
            navController = appNavController,
            modifier = modifier
        )
        register(
            profileNavigation,
            navController = appNavController,
            modifier = modifier
        )
        register(
            analyticsNavigation,
            navController = appNavController,
            modifier = modifier
        )
    }
}

@Composable
internal fun NavigationBarDestination.route(): String {
    val financesNavigation = getKoin().get<FinancesNavigation>()
    val profileNavigation = getKoin().get<ProfileNavigation>()
    val analyticsNavigation = getKoin().get<AnalyticsNavigation>()
    return when (this) {
        NavigationBarDestination.FINANCES -> financesNavigation.financesRoute
        NavigationBarDestination.ANALYTICS -> analyticsNavigation.analyticsRoute
        NavigationBarDestination.PROFILE -> profileNavigation.profileRoute
    }
}