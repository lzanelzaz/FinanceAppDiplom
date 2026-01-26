package project.e_buyankina.feature.operations.api.data.mappers

import project.e_buyankina.feature.operations.api.data.api.NewOperationApi
import project.e_buyankina.feature.operations.api.data.api.TransactionTypeApi
import project.e_buyankina.feature.operations.api.domain.NewOperation
import project.e_buyankina.feature.operations.api.domain.TransactionType

internal interface NewOperationDomainToApiMapper : (NewOperation) -> (NewOperationApi)

internal class NewOperationDomainToApiMapperImpl : NewOperationDomainToApiMapper {

    override fun invoke(domain: NewOperation): NewOperationApi = with(domain) {
        return NewOperationApi(
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