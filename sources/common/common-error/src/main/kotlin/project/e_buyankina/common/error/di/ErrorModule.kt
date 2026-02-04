package project.e_buyankina.common.error.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import project.e_buyankina.common.error.ErrorHandler
import project.e_buyankina.common.error.ErrorHandlerImpl
import project.e_buyankina.common.error.NotificationManager

val errorModule = module {
    singleOf(::ErrorHandlerImpl) { bind<NotificationManager>() }
    singleOf(::ErrorHandlerImpl) { bind<ErrorHandler>() }
}
