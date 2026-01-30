package project.e_buyankina.feature.finances.create_edit_operation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.joda.time.DateTime
import project.e_buyankina.feature.finances.R
import java.util.Locale
import project.e_buyankina.common.ui.R as CommonR

@Composable
internal fun DatePickerModal(
    state: UiState,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val localizedConfiguration = LocalConfiguration.current.apply { setLocale(Locale("ru")) }
    CompositionLocalProvider(
        LocalConfiguration provides localizedConfiguration,
        LocalContext provides LocalContext.current.createConfigurationContext(localizedConfiguration)
    ) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.selectedDateMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long) = DateTime(utcTimeMillis) <= DateTime.now()
                override fun isSelectableYear(year: Int) = DateTime.now().year >= year
            }
        )
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }) {
                    Text(stringResource(CommonR.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(CommonR.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState, title = {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    text = stringResource(R.string.select_date),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
            })
        }
    }
}