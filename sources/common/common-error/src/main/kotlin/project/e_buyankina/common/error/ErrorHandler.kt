package project.e_buyankina.common.error

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import project.e_buyankina.common.network.R
import project.e_buyankina.common.network.ServerException
import java.io.IOException

interface ErrorHandler {

    suspend fun handleError(throwable: Throwable)
}

internal sealed interface Notification {
    class Error(val message: (Context) -> String) : Notification
}

internal interface NotificationManager {

    val notifications: SharedFlow<Notification>
}

internal class ErrorHandlerImpl : ErrorHandler, NotificationManager {

    private val _notifications = MutableSharedFlow<Notification>()
    override val notifications = _notifications.asSharedFlow()

    override suspend fun handleError(throwable: Throwable) {
        val message = parseErrorMessage(throwable)
        Log.e("ErrorHandler", throwable.toString())
        _notifications.emit(Notification.Error(message))
    }

    private fun parseErrorMessage(throwable: Throwable): (Context) -> String {
        return when (throwable) {
            is ServerException -> { context -> throwable.message }
            is IOException -> { context -> context.getString(R.string.no_connection) }
            else -> { context -> context.getString(R.string.unknown_error) }
        }
    }
}