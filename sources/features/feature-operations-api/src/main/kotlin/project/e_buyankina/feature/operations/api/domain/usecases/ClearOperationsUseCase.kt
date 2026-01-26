package project.e_buyankina.feature.operations.api.domain.usecases

import project.e_buyankina.feature.operations.api.data.OperationsRepository

interface ClearOperationsUseCase {

    suspend operator fun invoke()
}

internal class ClearOperationsUseCaseImpl(
    private val repository: OperationsRepository,
) : ClearOperationsUseCase {

    override suspend fun invoke() {
        repository.deleteAll()
    }
}