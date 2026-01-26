package project.e_buyankina.feature.main.container.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.getKoin
import project.e_buyankina.common.navigation.features.FinancesNavigation
import project.e_buyankina.common.navigation.features.ProfileNavigation
import project.e_buyankina.common.navigation.register
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.common.ui.theme.AppTheme

@Composable
internal fun MainScreen(
    appNavController: NavHostController,
) {
    val nestedNavController = rememberNavController()
    val startDestination = NavigationBarDestination.FINANCES
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                NavigationBarDestination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            nestedNavController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                painterResource(destination.icon),
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(destination.label)) }
                    )
                }
            }
        }
    ) { paddingValues ->
        MainNavHost(
            nestedNavController,
            startDestination,
            appNavController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun MainNavHost(
    nestedNavController: NavHostController,
    startDestination: NavigationBarDestination,
    appNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val financesNavigation = getKoin().get<FinancesNavigation>()
    val profileNavigation = getKoin().get<ProfileNavigation>()
    NavHost(
        navController = nestedNavController,
        startDestination = startDestination.route
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
    }
}

@DayNightPreviews
@Composable
fun Preview() {
    AppTheme {
        MainScreen(rememberNavController())
    }
}
