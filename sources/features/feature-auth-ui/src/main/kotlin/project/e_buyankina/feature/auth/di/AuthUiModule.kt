package project.e_buyankina.feature.auth.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.common.navigation.features.AuthNavigation
import project.e_buyankina.feature.auth.navigation.AuthNavigationImpl
import project.e_buyankina.feature.auth.ui.AuthViewModel

val authUiModule = module {
    viewModelOf(::AuthViewModel)
    singleOf(::AuthNavigationImpl) { bind<AuthNavigation>() }
}