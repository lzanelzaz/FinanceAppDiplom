package project.e_buyankina.auth_api.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import project.e_buyankina.auth_api.data.AuthRepository
import project.e_buyankina.auth_api.data.AuthRepositoryImpl
import project.e_buyankina.auth_api.data.api.AuthService
import project.e_buyankina.auth_api.data.mappers.ProfileInfoApiToDbMapper
import project.e_buyankina.auth_api.data.mappers.ProfileInfoApiToDbMapperImpl
import project.e_buyankina.auth_api.data.mappers.ProfileInfoDbToDomainMapper
import project.e_buyankina.auth_api.data.mappers.ProfileInfoDbToDomainMapperImpl
import project.e_buyankina.auth_api.data.usecases.AuthorizeUseCaseImpl
import project.e_buyankina.auth_api.data.usecases.CreateUserUseCaseImpl
import project.e_buyankina.auth_api.data.usecases.GetCurrentUserUseCaseImpl
import project.e_buyankina.auth_api.data.usecases.LogOutUseCaseImpl
import project.e_buyankina.auth_api.domain.usecases.AuthorizeUseCase
import project.e_buyankina.auth_api.domain.usecases.CreateUserUseCase
import project.e_buyankina.auth_api.domain.usecases.GetCurrentUserUseCase
import project.e_buyankina.auth_api.domain.usecases.LogOutUseCase
import project.e_buyankina.common_network.ServiceCreator

val authApiModule = module {
    single<AuthService> { ServiceCreator.create(AuthService::class.java) }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::ProfileInfoApiToDbMapperImpl) { bind<ProfileInfoApiToDbMapper>() }
    singleOf(::ProfileInfoDbToDomainMapperImpl) { bind<ProfileInfoDbToDomainMapper>() }
    singleOf(::AuthorizeUseCaseImpl) { bind<AuthorizeUseCase>() }
    singleOf(::CreateUserUseCaseImpl) { bind<CreateUserUseCase>() }
    singleOf(::GetCurrentUserUseCaseImpl) { bind<GetCurrentUserUseCase>() }
    singleOf(::LogOutUseCaseImpl) { bind<LogOutUseCase>() }
}