package project.e_buyankina.auth_ui.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.auth_ui.AuthViewModel
import project.e_buyankina.auth_ui.navigation.AuthNavigationImpl
import project.e_buyankina.common.navigation.features.AuthNavigation

val authUiModule = module {
    viewModelOf(::AuthViewModel)
    singleOf(::AuthNavigationImpl) { bind<AuthNavigation>() }
}