package project.e_buyankina.feature.operations.api.data.mappers

import project.e_buyankina.feature.operations.api.data.api.OperationApi
import project.e_buyankina.feature.operations.api.data.api.TransactionTypeApi
import project.e_buyankina.feature.operations.api.domain.Operation
import project.e_buyankina.feature.operations.api.domain.TransactionType

internal interface OperationDomainToApiMapper : (Operation) -> (OperationApi)

internal class OperationDomainToApiMapperImpl : OperationDomainToApiMapper {

    override fun invoke(domain: Operation): OperationApi = with(domain) {
        return OperationApi(
            operationId = operationId,
            type = type.toApi(),
            amount = amount,
            date = date,
            subtype = subtype,
            description = description,
        )
    }

    private fun TransactionType.toApi(): TransactionTypeApi = when (this) {
        TransactionType.EXPENSE -> TransactionTypeApi.EXPENSE
        TransactionType.INCOME -> TransactionTypeApi.INCOME
    }
}