package project.e_buyankina.feature.operations.api.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import project.e_buyankina.common.network.ServiceCreator
import project.e_buyankina.feature.operations.api.data.OperationsRepository
import project.e_buyankina.feature.operations.api.data.OperationsRepositoryImpl
import project.e_buyankina.feature.operations.api.data.api.OperationsService
import project.e_buyankina.feature.operations.api.data.mappers.NewOperationDomainToApiMapper
import project.e_buyankina.feature.operations.api.data.mappers.NewOperationDomainToApiMapperImpl
import project.e_buyankina.feature.operations.api.data.mappers.OperationApiToDbMapper
import project.e_buyankina.feature.operations.api.data.mappers.OperationApiToDbMapperImpl
import project.e_buyankina.feature.operations.api.data.mappers.OperationDbToDomainMapper
import project.e_buyankina.feature.operations.api.data.mappers.OperationDbToDomainMapperImpl
import project.e_buyankina.feature.operations.api.data.mappers.OperationDomainToApiMapper
import project.e_buyankina.feature.operations.api.data.mappers.OperationDomainToApiMapperImpl
import project.e_buyankina.feature.operations.api.domain.usecases.ClearOperationsUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.ClearOperationsUseCaseImpl
import project.e_buyankina.feature.operations.api.domain.usecases.CreateOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.CreateOperationUseCaseImpl
import project.e_buyankina.feature.operations.api.domain.usecases.DeleteOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.DeleteOperationUseCaseImpl
import project.e_buyankina.feature.operations.api.domain.usecases.EditOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.EditOperationUseCaseImpl
import project.e_buyankina.feature.operations.api.domain.usecases.GetOperationUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.GetOperationUseCaseImpl
import project.e_buyankina.feature.operations.api.domain.usecases.SubscribeToOperationsUseCase
import project.e_buyankina.feature.operations.api.domain.usecases.SubscribeToOperationsUseCaseImpl

val operationsApiModule = module {
    single<OperationsService> { ServiceCreator.create(OperationsService::class.java) }
    singleOf(::OperationsRepositoryImpl) { bind<OperationsRepository>() }

    singleOf(::OperationApiToDbMapperImpl) { bind<OperationApiToDbMapper>() }
    singleOf(::OperationDbToDomainMapperImpl) { bind<OperationDbToDomainMapper>() }
    singleOf(::NewOperationDomainToApiMapperImpl) { bind<NewOperationDomainToApiMapper>() }
    singleOf(::OperationDomainToApiMapperImpl) { bind<OperationDomainToApiMapper>() }

    singleOf(::ClearOperationsUseCaseImpl) { bind<ClearOperationsUseCase>() }
    singleOf(::CreateOperationUseCaseImpl) { bind<CreateOperationUseCase>() }
    singleOf(::DeleteOperationUseCaseImpl) { bind<DeleteOperationUseCase>() }
    singleOf(::EditOperationUseCaseImpl) { bind<EditOperationUseCase>() }
    singleOf(::GetOperationUseCaseImpl) { bind<GetOperationUseCase>() }
    singleOf(::SubscribeToOperationsUseCaseImpl) { bind<SubscribeToOperationsUseCase>() }
}