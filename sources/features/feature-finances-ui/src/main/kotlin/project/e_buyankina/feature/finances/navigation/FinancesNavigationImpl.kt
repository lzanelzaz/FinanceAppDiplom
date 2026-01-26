package project.e_buyankina.feature.finances.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import project.e_buyankina.common.navigation.features.FinancesNavigation
import project.e_buyankina.feature.finances.create_edit_operation.CreateOrEditOperationScreen
import project.e_buyankina.feature.finances.ui.FinancesScreen

internal class FinancesNavigationImpl : FinancesNavigation {

    override val financesRoute = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            FinancesScreen(navController, modifier)
        }
        navGraphBuilder.composable(createOrEditOperationRoute) {
            CreateOrEditOperationScreen(modifier)
        }
    }
}

private const val baseRoute = "financesRoute"

internal const val createOrEditOperationRoute = "createOrEditOperation"