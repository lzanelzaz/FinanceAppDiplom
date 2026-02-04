package project.e_buyankina.common.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    abstract val errorHandler: ErrorHandler
}

fun BaseViewModel.safeLaunch(onError: () -> Unit = {}, block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch {
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            errorHandler.handleError(e)
            onError()
        }
    }
}