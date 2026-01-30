package project.e_buyankina.feature.analytics.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
internal fun AnalyticsScreen(modifier: Modifier = Modifier) {

    var showDatePicker by remember { mutableStateOf(false) }
    if (showDatePicker) {
//        DateRangePickerModal(
//            state = state,
//            onDateSelected = onDateUpdated,
//            onDismiss = { showDatePicker = false }
//        )
    }
}