package project.e_buyankina.feature.analytics.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import project.e_buyankina.feature.analytics.R
import java.util.Locale
import project.e_buyankina.common.ui.R as CommonR

@Composable
internal fun DateRangePickerModal(
    state: UiState,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val localizedConfiguration = LocalConfiguration.current.apply { setLocale(Locale("ru")) }
    CompositionLocalProvider(
        LocalConfiguration provides localizedConfiguration,
        LocalContext provides LocalContext.current.createConfigurationContext(localizedConfiguration)
    ) {
        val dateRangePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = state.startDateMillis,
            initialSelectedEndDateMillis = state.endDateMillis,
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
                    Text(stringResource(CommonR.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(CommonR.string.cancel))
                }
            }
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                showModeToggle = false,
                title = {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        text = stringResource(R.string.select_date_range),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            )
        }
    }
}