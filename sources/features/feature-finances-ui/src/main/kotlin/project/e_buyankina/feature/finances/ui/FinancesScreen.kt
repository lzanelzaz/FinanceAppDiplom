package project.e_buyankina.feature.finances.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.create_edit_operation.CreateOrEditOperationScreen
import project.e_buyankina.feature.finances.ui.UiState.UiOperation

@Composable
internal fun FinancesScreen(
    nestedNavController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<FinancesViewModel>()
    val state = viewModel.uiState.collectAsState()

    FinancesContent(
        modifier,
        state.value,
        {

        },
    )
}

@Composable
internal fun FinancesContent(
    modifier: Modifier = Modifier,
    state: UiState,
    onOperationClick: (UiOperation) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                shape = CircleShape,
            ) {
                Icon(
                    painterResource(R.drawable.edit_24dp),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {
            LazyColumn {
                items(state.operations, UiOperation::operationId) {
                    Operation(it, onOperationClick)
                }
            }
            if (showBottomSheet) {
                CreateOrEditOperationScreen(Modifier) { newValue -> showBottomSheet = newValue }
            }
        }
    }

}

@Composable
private fun Operation(
    operation: UiOperation,
    onOperationClick: (UiOperation) -> Unit,
) {

}

@DayNightPreviews
@Composable
private fun Preview() {
    FinancesScreen(
        rememberNavController()
    )
}