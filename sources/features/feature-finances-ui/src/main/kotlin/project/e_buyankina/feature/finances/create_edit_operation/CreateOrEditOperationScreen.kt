package project.e_buyankina.feature.finances.create_edit_operation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import project.e_buyankina.common.ui.loadingbutton.LoadingButton
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.common.ui.theme.AppTheme
import project.e_buyankina.feature.finances.R
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.common.Type

@Composable
internal fun CreateOrEditOperationScreen(
    modifier: Modifier = Modifier,
    operationId: String? = null,
    showBottomSheetUpdate: (Boolean) -> Unit,
) {
//            Button(onClick = {
//                scope.launch { sheetState.hide() }.invokeOnCompletion {
//                    if (!sheetState.isVisible) {
//                        showBottomSheetUpdate(false)
//                    }
//                }
//            }) {
//                Text("Hide bottom sheet")
//            }
    val viewModel = koinViewModel<CreateOrEditOperationViewModel>()
    LaunchedEffect(Unit) {
        viewModel.load(operationId)
    }
    val state = viewModel.uiState.collectAsState()

    CreateOrEditOperationContent(
        modifier = modifier,
        state = state.value,
        showBottomSheetUpdate = showBottomSheetUpdate,
        onTypeChanged = viewModel::onTypeChanged,
        onSubtypeChanged = viewModel::onSubtypeChanged,
        onDateUpdated = viewModel::onDateChanged,
        onKeyClicked = viewModel::onKeyClicked,
        onSaveClick = viewModel::onSaveClick,
        onDeleteClick = viewModel::onDeleteClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateOrEditOperationContent(
    modifier: Modifier = Modifier,
    state: UiState,
    showBottomSheetUpdate: (Boolean) -> Unit = {},
    onTypeChanged: (Type) -> Unit = {},
    onSubtypeChanged: (Subtype) -> Unit = {},
    onDateUpdated: (Long?) -> Unit = {},
    onKeyClicked: (UiState.KeyBoardItem) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var showDatePicker by remember { mutableStateOf(false) }
    ModalBottomSheet(
        onDismissRequest = { showBottomSheetUpdate(false) },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = modifier.padding(bottom = 8.dp),
        ) {
            var selectedType by remember { mutableStateOf(state.selectedType) }
            var selectedSubtype by remember { mutableStateOf(state.selectedSubtype) }
            TypeBlock(
                state = state,
                isSelected = { selectedType == it },
                selectedChanged = {
                    selectedType = it
                    onTypeChanged(it)
                },
            )
            SubtypesBlock(
                state = state,
                isSelected = { selectedSubtype == it },
                selectedChanged = { selectedSubtype = it },
            )
            DateAmountBlock(
                state = state,
                onShowDatePicker = { showDatePicker = true },
            )
            TextFieldsBlock(state)
            KeyboardBlock(state, onKeyClicked)
            ButtonsBlock(
                onSaveClick = onSaveClick, onDeleteClick = onDeleteClick
            )
        }
    }
    if (showDatePicker) {
        DatePickerModal(
            state = state,
            onDateSelected = onDateUpdated,
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TypeBlock(
    state: UiState,
    isSelected: (Type) -> Boolean,
    selectedChanged: (Type) -> Unit,
) {
    Row(
        Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        state.types.forEachIndexed { index, type ->
            ToggleButton(
                checked = isSelected(type),
                onCheckedChange = { selectedChanged(type) },
                modifier = Modifier.weight(1f),
                shapes = when (index) {
                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    else -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                },
            ) {
                if (isSelected(type)) {
                    Icon(
                        painterResource(R.drawable.check_24dp),
                        contentDescription = null,
                    )
                    Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                }
                Text(stringResource(type.text))
            }
        }
    }
}

@Composable
private fun SubtypesBlock(
    state: UiState,
    isSelected: (Subtype) -> Boolean,
    selectedChanged: (Subtype) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        items(state.subtypes, { subtype -> subtype.text }) {
            Subtype(it, isSelected, selectedChanged)
        }
    }
}

@Composable
private fun DateAmountBlock(
    state: UiState,
    onShowDatePicker: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(onShowDatePicker, modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(state.selectedDate)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                state.amount,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}

@Composable
private fun TextFieldsBlock(
    state: UiState,
) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        label = {
            Text(text = stringResource(R.string.details))
        },
    )
}

@Composable
private fun KeyboardBlock(
    state: UiState,
    onKeyClicked: (UiState.KeyBoardItem) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(8.dp),
        userScrollEnabled = false,
    ) {
        items(state.keyboard) {
            Key(it, onKeyClicked)
        }
    }
}

@Composable
private fun ButtonsBlock(
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        LoadingButton(
            {},
            isLoading = false,
            modifier = Modifier.width(220.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = stringResource(R.string.save),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        LoadingButton(
            {}, isLoading = false,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
            ),
            loadingColor = MaterialTheme.colorScheme.onError,
        ) {
            Icon(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(R.drawable.delete_24dp),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun Subtype(
    item: Subtype,
    isSelected: (Subtype) -> Boolean,
    selectedChanged: (Subtype) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable(onClick = { selectedChanged(item) })
                .clip(CircleShape)
                .background(
                    if (isSelected(item)) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    }
                )
        ) {
            Icon(
                painterResource(item.icon),
                contentDescription = null,
                modifier = Modifier.padding(12.dp),
            )
        }
        Text(
            stringResource(item.text),
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Composable
private fun Key(
    item: UiState.KeyBoardItem,
    onClick: (UiState.KeyBoardItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick(item) })
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.outlineVariant),
        contentAlignment = Alignment.Center
    ) {
        when (item) {
            is UiState.KeyBoardItem.Digit -> Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = item.digit.toString(),
                style = MaterialTheme.typography.headlineMedium,
            )

            is UiState.KeyBoardItem.Point -> Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = item.char,
                style = MaterialTheme.typography.headlineMedium,
            )

            is UiState.KeyBoardItem.Backspace -> Icon(
                modifier = Modifier.padding(vertical = 10.dp),
                painter = painterResource(item.icon),
                contentDescription = null,
            )
        }
    }
}

@DayNightPreviews
@Composable
private fun Preview() {
    val initUi = UiState(
        selectedDate = "27.01.2026",
        selectedDateMillis = 0,
        selectedType = Type.EXPENSE,
        selectedSubtype = Subtype.Expense.ENTERTAINMENT,
        types = Type.entries,
        subtypes = Subtype.Expense.entries,
        amount = "1 000 ₽",
        details = null,
        keyboard = listOf(
            UiState.KeyBoardItem.Digit(1),
            UiState.KeyBoardItem.Digit(2),
            UiState.KeyBoardItem.Digit(3),
            UiState.KeyBoardItem.Digit(4),
            UiState.KeyBoardItem.Digit(5),
            UiState.KeyBoardItem.Digit(6),
            UiState.KeyBoardItem.Digit(7),
            UiState.KeyBoardItem.Digit(8),
            UiState.KeyBoardItem.Digit(9),
            UiState.KeyBoardItem.Point(","),
            UiState.KeyBoardItem.Digit(0),
            UiState.KeyBoardItem.Backspace(R.drawable.backspace_24dp),
        ),
    )
    AppTheme {
        CreateOrEditOperationContent(Modifier, initUi) {}
    }
}