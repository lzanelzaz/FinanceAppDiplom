package project.e_buyankina.financeapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import project.e_buyankina.app_database.appDatabaseModule
import project.e_buyankina.auth_ui.di.authUiModule
import project.e_buyankina.feature.auth.api.di.authApiModule
import project.e_buyankina.main.di.mainModule
import project.e_buyankina.splashscreen.di.splashModule

class FinanceApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FinanceApp)
            modules(koinModules)
        }
    }
}

val koinModules = module {
    includes(
        appDatabaseModule,
        splashModule,
        authApiModule,
        authUiModule,
        mainModule,
    )
}