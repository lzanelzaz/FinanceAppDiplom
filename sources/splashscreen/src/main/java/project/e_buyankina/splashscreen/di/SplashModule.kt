package project.e_buyankina.splashscreen.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.common.navigation.features.SplashNavigation
import project.e_buyankina.splashscreen.SplashViewModel
import project.e_buyankina.splashscreen.navigation.SplashNavigationImpl

val splashModule = module {
    viewModelOf(::SplashViewModel)
    singleOf(::SplashNavigationImpl) { bind<SplashNavigation>() }
}