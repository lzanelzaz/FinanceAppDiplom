package project.e_buyankina.feature.analytics.ui

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import project.e_buyankina.common.ui.R
import java.util.Locale

@Composable
internal fun DateRangePickerModal(
    state: State,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val localizedConfiguration = LocalConfiguration.current.apply { setLocale(Locale("ru")) }
    CompositionLocalProvider(
        LocalConfiguration provides localizedConfiguration,
        LocalContext provides LocalContext.current.createConfigurationContext(localizedConfiguration)
    ) {
        val dateRangePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = state.startDate.millis,
            initialSelectedEndDateMillis = state.endDate.millis,
        )
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateRangeSelected(
                            Pair(dateRangePickerState.selectedStartDateMillis, dateRangePickerState.selectedEndDateMillis)
                        )
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                showModeToggle = false,
            )
        }
    }
}