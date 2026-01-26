package project.e_buyankina.feature.operations.api.domain.usecases

import project.e_buyankina.feature.operations.api.data.OperationsRepository
import project.e_buyankina.feature.operations.api.domain.Operation

interface EditOperationUseCase {

    suspend operator fun invoke(
        accountId: String,
        operation: Operation,
    )
}

internal class EditOperationUseCaseImpl(
    private val repository: OperationsRepository,
) : EditOperationUseCase {

    override suspend fun invoke(accountId: String, operation: Operation) {
        repository.editOperation(accountId, operation)
    }
}