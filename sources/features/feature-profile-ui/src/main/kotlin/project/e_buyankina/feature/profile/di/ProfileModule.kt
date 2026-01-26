package project.e_buyankina.feature.profile.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.common.navigation.features.ProfileNavigation
import project.e_buyankina.feature.profile.navigation.ProfileNavigationImpl
import project.e_buyankina.feature.profile.ui.ProfileViewModel

val profileModule = module {
    viewModelOf(::ProfileViewModel)
    singleOf(::ProfileNavigationImpl) { bind<ProfileNavigation>() }
}
