package project.e_buyankina.feature.operations.api.data.mappers

import project.e_buyankina.feature.operations.api.data.api.OperationApi
import project.e_buyankina.feature.operations.api.data.api.TransactionTypeApi
import project.e_buyankina.feature.operations.api.data.db.OperationDb
import project.e_buyankina.feature.operations.api.domain.TransactionType

internal interface OperationApiToDbMapper : (String, OperationApi) -> (OperationDb)

internal class OperationApiToDbMapperImpl : OperationApiToDbMapper {

    override fun invoke(accountId: String, api: OperationApi): OperationDb = with(api) {
        return OperationDb(
            operationId = operationId,
            accountId = accountId,
            type = type.toDb(),
            amount = amount,
            date = date,
            subtype = subtype,
            description = description,
        )
    }

    private fun TransactionTypeApi.toDb(): TransactionType = when (this) {
        TransactionTypeApi.EXPENSE -> TransactionType.EXPENSE
        TransactionTypeApi.INCOME -> TransactionType.INCOME
    }
}