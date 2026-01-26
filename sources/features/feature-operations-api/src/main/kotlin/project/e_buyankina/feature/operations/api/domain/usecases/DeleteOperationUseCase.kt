package project.e_buyankina.feature.operations.api.domain.usecases

import project.e_buyankina.feature.operations.api.data.OperationsRepository

interface DeleteOperationUseCase {

    suspend operator fun invoke(
        accountId: String,
        operationId: String,
    )
}

internal class DeleteOperationUseCaseImpl(
    private val repository: OperationsRepository,
) : DeleteOperationUseCase {

    override suspend fun invoke(accountId: String, operationId: String) {
        repository.deleteOperation(accountId, operationId)
    }
}