package project.e_buyankina.feature.main.container

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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import project.e_buyankina.common_ui.preview.DayNightPreviews
import project.e_buyankina.common_ui.theme.AppTheme

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
        AppNavHost(
            nestedNavController,
            startDestination,
            appNavController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun AppNavHost(
    nestedNavController: NavHostController,
    startDestination: NavigationBarDestination,
    appNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = nestedNavController,
        startDestination = startDestination.route
    ) {
        NavigationBarDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
//                    Destination.FINANCES -> SongsScreen()
//                    Destination.ANALYTICS -> AlbumScreen()
//                    Destination.PROFILE -> ProfileScreen(modifier, appNavController)
                    else -> {}
                }
            }
        }
    }
}

@DayNightPreviews
@Composable
fun Preview() {
    AppTheme {
        MainScreen(rememberNavController())
    }
}
