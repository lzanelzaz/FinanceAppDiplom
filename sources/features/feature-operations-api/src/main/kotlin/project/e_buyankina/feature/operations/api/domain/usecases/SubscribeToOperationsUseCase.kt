package project.e_buyankina.feature.operations.api.domain.usecases

import kotlinx.coroutines.flow.Flow
import project.e_buyankina.feature.operations.api.data.OperationsRepository
import project.e_buyankina.feature.operations.api.domain.Operation

interface SubscribeToOperationsUseCase {

    operator fun invoke(
        accountId: String,
    ): Flow<List<Operation>>
}

internal class SubscribeToOperationsUseCaseImpl(
    private val repository: OperationsRepository,
) : SubscribeToOperationsUseCase {

    override fun invoke(accountId: String): Flow<List<Operation>> {
        return repository.getOperations(accountId)
    }
}