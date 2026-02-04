package project.e_buyankina.feature.operations.api.domain.usecases

import project.e_buyankina.feature.operations.api.data.OperationsRepository
import project.e_buyankina.feature.operations.api.domain.PageOperations

interface GetOperationsPageUseCase {

    suspend operator fun invoke(
        accountId: String,
        page: Int,
    ): PageOperations
}

internal class GetOperationsPageUseCaseImpl(
    private val repository: OperationsRepository,
) : GetOperationsPageUseCase {

    override suspend fun invoke(accountId: String, page: Int): PageOperations {
        return repository.getOperationsPage(accountId, page)
    }
}