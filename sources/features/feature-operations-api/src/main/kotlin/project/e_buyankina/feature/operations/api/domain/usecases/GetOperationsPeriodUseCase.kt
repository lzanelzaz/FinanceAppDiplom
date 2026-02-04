package project.e_buyankina.feature.operations.api.domain.usecases

import org.joda.time.DateTime
import project.e_buyankina.feature.operations.api.data.OperationsRepository
import project.e_buyankina.feature.operations.api.domain.Operation

interface GetOperationsPeriodUseCase {

    suspend operator fun invoke(
        accountId: String,
        startDate: DateTime,
        endDate: DateTime,
    ): List<Operation>
}

internal class GetOperationsPeriodUseCaseImpl(
    private val repository: OperationsRepository,
) : GetOperationsPeriodUseCase {

    override suspend fun invoke(accountId: String, startDate: DateTime, endDate: DateTime): List<Operation> {
        return repository.getOperationsPeriod(accountId, startDate, endDate)
    }
}