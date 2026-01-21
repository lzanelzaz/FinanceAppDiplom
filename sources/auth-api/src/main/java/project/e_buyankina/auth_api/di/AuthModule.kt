package project.e_buyankina.auth_api.di

import org.koin.dsl.module
import project.e_buyankina.auth_api.AuthService
import project.e_buyankina.common_network.ServiceCreator

val authApiModule = module {
    single<AuthService> { ServiceCreator.create(AuthService::class.java) }
}