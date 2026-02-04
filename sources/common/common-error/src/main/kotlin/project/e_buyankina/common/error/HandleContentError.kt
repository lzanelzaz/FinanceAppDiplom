package project.e_buyankina.common.error

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import org.koin.compose.getKoin

@Composable
fun HandleContentError(content: @Composable ((PaddingValues) -> Unit)) {
    val notificationManager = getKoin().get<NotificationManager>()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        LaunchedEffect(Unit) {
            notificationManager.notifications.collect { notification ->
                when (notification) {
                    is Notification.Error -> {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = notification.message(context),
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                }
            }
        }
        content(contentPadding)
    }
}
