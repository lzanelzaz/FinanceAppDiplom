package project.e_buyankina.auth_ui.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.auth_ui.AuthViewModel

val authUiModule = module {
    viewModelOf(::AuthViewModel)
}