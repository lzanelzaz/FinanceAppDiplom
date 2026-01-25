package project.e_buyankina.feature.main.container.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import project.e_buyankina.common.navigation.features.MainNavigation
import project.e_buyankina.feature.main.container.navigation.MainNavigationImpl

val mainModule = module {
    singleOf(::MainNavigationImpl) { bind<MainNavigation>() }
}
