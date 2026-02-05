package project.e_buyankina.common.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(), contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        shape = RoundedCornerShape(16.dp),
                    )
                }
            }
        },
    ) { contentPadding ->
        LaunchedEffect(Unit) {
            notificationManager.notifications.collect { notification ->
                when (notification) {
                    is Notification.Error -> {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = notification.message(context), duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        }
        content(contentPadding)
    }
}
