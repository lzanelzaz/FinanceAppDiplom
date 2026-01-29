package project.e_buyankina.feature.finances.create_edit_operation

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import org.joda.time.DateTime
import java.util.Locale

@Composable
internal fun DatePickerModal(
    state: UiState,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val localizedConfiguration = remember(Locale("ru")) {
        Configuration(configuration).apply {
            setLocale(locale)
        }
    }
    CompositionLocalProvider(
        LocalConfiguration provides localizedConfiguration,
        LocalContext provides context.createConfigurationContext(localizedConfiguration)
    ) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = DateTime.parse(state.selectedDate).millis,
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
}