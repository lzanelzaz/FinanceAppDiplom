package project.e_buyankina.main.navigation

import androidx.compose.foundation.layout.statusBarsPadding
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
import project.e_buyankina.main.profile.ProfileScreen

@Composable
internal fun MainScreen(
    onOpenAuth: () -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = Destination.FINANCES
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
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
    ) { _ ->
        AppNavHost(
            navController,
            startDestination,
            onOpenAuth,
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    onOpenAuth: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
//                    Destination.FINANCES -> SongsScreen()
//                    Destination.ANALYTICS -> AlbumScreen()
                    Destination.PROFILE -> ProfileScreen(modifier, onOpenAuth)
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
        MainScreen {}
    }
}
