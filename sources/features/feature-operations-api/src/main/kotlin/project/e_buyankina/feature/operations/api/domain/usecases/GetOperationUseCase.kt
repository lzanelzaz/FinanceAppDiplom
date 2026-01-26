package project.e_buyankina.feature.operations.api.domain.usecases

import project.e_buyankina.feature.operations.api.data.OperationsRepository
import project.e_buyankina.feature.operations.api.domain.Operation

interface GetOperationUseCase {

    suspend operator fun invoke(
        operationId: String,
    ): Operation?
}

internal class GetOperationUseCaseImpl(
    private val repository: OperationsRepository,
) : GetOperationUseCase {

    override suspend fun invoke(operationId: String): Operation? {
        return repository.getOperation(operationId)
    }
}