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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import project.e_buyankina.feature.finances.common.Expense
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
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.load(operationId) {
            context.getString(it)
        }
    }

    CreateOrEditOperationContent(
        modifier = modifier,
        state = initUi(),
        showBottomSheetUpdate,
        {},

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateOrEditOperationContent(
    modifier: Modifier = Modifier,
    state: UiState,
    showBottomSheetUpdate: (Boolean) -> Unit = {},
    onStateChanged: State.() -> Unit = {},
    onDateUpdated: (Long) -> Unit = {},
    onKeyClicked: (UiState.KeyBoardItem) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    ModalBottomSheet(
        onDismissRequest = { showBottomSheetUpdate(false) },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = modifier.padding(bottom = 8.dp),
        ) {
            var selectedTypeIndex by remember { mutableIntStateOf(state.selectedType.ordinal) }
            var selectedSubtypeIndex by remember { mutableIntStateOf(state.selectedSubtype.index) }
            var showDatePicker = remember { false }
            TypeBlock(
                state = state,
                isSelected = { selectedTypeIndex == it },
                selectedChanged = { selectedTypeIndex = it },
            )
            SubtypesBlock(
                state = state,
                isSelected = { selectedSubtypeIndex == it },
                selectedChanged = { selectedSubtypeIndex = it },
            )
            DateAmountBlock(
                state = state,
                onShowDatePicker = { showDatePicker = true },
                onDateUpdated = onDateUpdated,
            )
            if (showDatePicker) {
                DatePickerModal(
                    onDateSelected = { },
                    onDismiss = { showDatePicker = false }
                )
            }
            TextFieldsBlock(state)
            KeyboardBlock(state, onKeyClicked)
            ButtonsBlock(
                onSaveClick = onSaveClick, onDeleteClick = onDeleteClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TypeBlock(
    state: UiState,
    isSelected: (Int) -> Boolean,
    selectedChanged: (Int) -> Unit,
) {
    Row(
        Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        state.types.forEachIndexed { index, type ->
            ToggleButton(
                checked = isSelected(index),
                onCheckedChange = { selectedChanged(index) },
                modifier = Modifier.weight(1f),
                shapes = when (index) {
                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    else -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                },
            ) {
                if (isSelected(index)) {
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
    isSelected: (Int) -> Boolean,
    selectedChanged: (Int) -> Unit,
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
    onDateUpdated: (Long) -> Unit,
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
    isSelected: (Int) -> Boolean,
    selectedChanged: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable(onClick = { selectedChanged(item.index) })
                .clip(CircleShape)
                .background(
                    if (isSelected(item.index)) {
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

@Composable
private fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun initUi() = UiState(
    selectedDate = "27.01.2026",
    selectedType = Type.EXPENSE,
    selectedSubtype = Expense.ENTERTAINMENT,
    types = Type.entries,
    subtypes = Expense.entries,
    amount = "1 000 ₽",
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


@DayNightPreviews
@Composable
private fun Preview() {
    AppTheme {
        CreateOrEditOperationContent(Modifier, initUi()) {}
    }
}