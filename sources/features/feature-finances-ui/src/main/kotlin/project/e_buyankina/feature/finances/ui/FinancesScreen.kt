package project.e_buyankina.feature.finances.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.navigation.createOrEditOperationRoute

@Composable
internal fun FinancesScreen(
    nestedNavController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        LazyColumn {

        }
        FloatingActionButton(
            onClick = {
                nestedNavController.navigate(createOrEditOperationRoute)
            },
            shape = CircleShape,
        ) {
            Icon(
                painterResource(R.drawable.edit_24dp),
                contentDescription = null
            )
        }
    }
}

@DayNightPreviews
@Composable
private fun Preview() {
    FinancesScreen(
        rememberNavController()
    )
}