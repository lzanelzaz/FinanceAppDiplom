package project.e_buyankina.feature.finances.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
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
import org.koin.compose.viewmodel.koinViewModel
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.common.ui.theme.AppTheme
import project.e_buyankina.common.ui.theme.moneyGreen
import project.e_buyankina.common.ui.theme.moneyRed
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.create_edit_operation.CreateOrEditOperationScreen
import project.e_buyankina.feature.finances.ui.UiState.UiOperation

@Composable
internal fun FinancesScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<FinancesViewModel>()
    val state = viewModel.uiState.collectAsState()

    FinancesContent(
        modifier.fillMaxSize(),
        state.value,
    )
}

@Composable
internal fun FinancesContent(
    modifier: Modifier = Modifier,
    state: UiState,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var clickedOperationId: String? by remember { mutableStateOf(null) }
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    clickedOperationId = null
                    showBottomSheet = true
                },
                shape = CircleShape,
            ) {
                Icon(
                    painterResource(R.drawable.edit_24dp),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
        ) {
            LazyColumn {
                state.operationsGrouped.forEach { grouped ->
                    stickyHeader { Date(grouped.date) }
                    items(grouped.operations, { it.operationId }) {
                        Operation(it) { operation ->
                            clickedOperationId = operation.operationId
                            showBottomSheet = true
                        }
                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        CreateOrEditOperationScreen(Modifier, clickedOperationId) { newValue ->
            clickedOperationId = null
            showBottomSheet = newValue
        }
    }
}

@Composable
private fun LazyItemScope.Date(
    date: String
) {
    Text(
        modifier = Modifier
            .animateItem()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
            .padding(vertical = 10.dp, horizontal = 16.dp),
        text = date,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun LazyItemScope.Operation(
    operation: UiOperation,
    onOperationClick: (UiOperation) -> Unit,
) {
    Row(
        Modifier
            .animateItem()
            .clickable { onOperationClick(operation) }
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .height(48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
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
                    color = if (operation.isAmountColorPositive) moneyGreen else moneyRed
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
    val initState = UiState.OperationsGrouped(
        "11.11.2025",
        listOf(
            UiOperation(
                operationId = "",
                amount = "+1 000 000 ₽",
                subtype = Subtype.Expense.ENTERTAINMENT,
                description = "Описание wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
                isAmountColorPositive = true,
            )
        )
    )
    AppTheme {
        FinancesContent(
            Modifier,
            UiState(listOf(initState))
        )
    }
}