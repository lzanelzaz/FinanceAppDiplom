package project.e_buyankina.feature.operations.api.data.mappers

import project.e_buyankina.feature.operations.api.data.db.OperationDb
import project.e_buyankina.feature.operations.api.domain.Operation

internal interface OperationDbToDomainMapper : (OperationDb) -> (Operation)

internal class OperationDbToDomainMapperImpl : OperationDbToDomainMapper {

    override fun invoke(db: OperationDb): Operation = with(db) {
        return Operation(
            operationId = operationId,
            type = type,
            amount = amount,
            date = date,
            subtype = subtype,
            description = description,
        )
    }
}