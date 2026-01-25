package project.e_buyankina.splashscreen.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.splashscreen.SplashViewModel

val splashModule = module {
    viewModelOf(::SplashViewModel)
}