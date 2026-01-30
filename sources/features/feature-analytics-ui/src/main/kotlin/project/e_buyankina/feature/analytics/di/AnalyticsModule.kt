package project.e_buyankina.feature.analytics.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.common.navigation.features.AnalyticsNavigation
import project.e_buyankina.feature.analytics.navigation.AnalyticsNavigationImpl
import project.e_buyankina.feature.analytics.ui.AnalyticsViewModel

val analyticsModule = module {
    viewModelOf(::AnalyticsViewModel)
    singleOf(::AnalyticsNavigationImpl) { bind<AnalyticsNavigation>() }
}
