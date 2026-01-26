package project.e_buyankina.feature.operations.api.domain.usecases

import project.e_buyankina.feature.operations.api.data.OperationsRepository
import project.e_buyankina.feature.operations.api.domain.NewOperation

interface CreateOperationUseCase {

    suspend operator fun invoke(
        accountId: String,
        newOperation: NewOperation,
    )
}

internal class CreateOperationUseCaseImpl(
    private val repository: OperationsRepository,
) : CreateOperationUseCase {

    override suspend fun invoke(accountId: String, newOperation: NewOperation) {
        repository.createOperation(accountId, newOperation)
    }
}