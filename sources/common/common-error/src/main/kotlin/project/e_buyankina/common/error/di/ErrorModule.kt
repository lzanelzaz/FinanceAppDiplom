package project.e_buyankina.common.error.di

import org.koin.dsl.binds
import org.koin.dsl.module
import project.e_buyankina.common.error.ErrorHandler
import project.e_buyankina.common.error.ErrorHandlerImpl
import project.e_buyankina.common.error.NotificationManager

val errorModule = module {
    single { ErrorHandlerImpl() } binds arrayOf(NotificationManager::class, ErrorHandler::class)
}
