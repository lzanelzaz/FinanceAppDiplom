package project.e_buyankina.feature.profile.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.main.profile.ProfileViewModel

val profileModule = module {
    viewModelOf(::ProfileViewModel)
}
