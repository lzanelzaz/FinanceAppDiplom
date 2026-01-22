package project.e_buyankina.auth_api.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import project.e_buyankina.auth_api.data.AuthRepository
import project.e_buyankina.auth_api.data.AuthRepositoryImpl
import project.e_buyankina.auth_api.data.AuthService
import project.e_buyankina.common_network.ServiceCreator

val authApiModule = module {
    single<AuthService> { ServiceCreator.create(AuthService::class.java) }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
}