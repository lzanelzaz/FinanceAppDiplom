package project.e_buyankina.feature.finances.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.common.ui.theme.AppTheme
import project.e_buyankina.common.ui.theme.moneyRed
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.common.Expense
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
    Row(
        Modifier
            .padding(16.dp)
            .height(48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable(onClick = { })
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.outlineVariant)
        ) {
            Icon(
                painterResource(operation.subtype.icon),
                contentDescription = null,
                modifier = Modifier.padding(12.dp),
            )
        }
        Column(
            Modifier
                .fillMaxHeight()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(operation.subtype.text),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = operation.amount,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = moneyRed
                )
            }
            operation.description?.let {
                Text(
                    modifier = Modifier,
                    text = it,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@DayNightPreviews
@Composable
private fun PreviewOperation() {
    AppTheme {
        Operation(
            initState()
        ) {}
    }
}

private fun initState() = UiOperation(
    operationId = "",
    amount = "+1 000 000 ₽",
    date = "27.01.2026",
    subtype = Expense.ENTERTAINMENT,
    description = "Описание wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"
)
