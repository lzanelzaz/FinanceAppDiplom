package project.e_buyankina.feature.splashscreen.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.common.navigation.features.SplashNavigation
import project.e_buyankina.feature.splashscreen.navigation.SplashNavigationImpl
import project.e_buyankina.feature.splashscreen.ui.SplashViewModel

val splashModule = module {
    viewModelOf(::SplashViewModel)
    singleOf(::SplashNavigationImpl) { bind<SplashNavigation>() }
}