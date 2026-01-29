package project.e_buyankina.feature.operations.api.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import project.e_buyankina.common.network.retrofitErrorHandler
import project.e_buyankina.feature.operations.api.data.api.OperationsService
import project.e_buyankina.feature.operations.api.data.db.OperationDb
import project.e_buyankina.feature.operations.api.data.db.OperationsDao
import project.e_buyankina.feature.operations.api.data.mappers.NewOperationDomainToApiMapper
import project.e_buyankina.feature.operations.api.data.mappers.OperationApiToDbMapper
import project.e_buyankina.feature.operations.api.data.mappers.OperationDbToDomainMapper
import project.e_buyankina.feature.operations.api.data.mappers.OperationDomainToApiMapper
import project.e_buyankina.feature.operations.api.domain.NewOperation
import project.e_buyankina.feature.operations.api.domain.Operation

internal interface OperationsRepository {

    suspend fun createOperation(
        accountId: String,
        newOperation: NewOperation,
    )

    suspend fun editOperation(
        accountId: String,
        operation: Operation,
    )

    suspend fun deleteOperation(
        accountId: String,
        operationId: String,
    )

    suspend fun deleteAll()

    suspend fun getOperation(
        operationId: String,
    ): Operation?

    fun getOperations(
        accountId: String,
    ): Flow<List<Operation>>
}

internal class OperationsRepositoryImpl(
    private val service: OperationsService,
    private val dao: OperationsDao,
    private val operationApiToDbMapper: OperationApiToDbMapper,
    private val operationDbToDomainMapper: OperationDbToDomainMapper,
    private val newOperationDomainToApiMapper: NewOperationDomainToApiMapper,
    private val operationDomainToApiMapper: OperationDomainToApiMapper,
) : OperationsRepository {

    override suspend fun createOperation(
        accountId: String,
        newOperation: NewOperation
    ) {
        val newOperationApi = newOperationDomainToApiMapper(newOperation)
        val api = retrofitErrorHandler(service.createOperation(accountId, newOperationApi))
        val db = operationApiToDbMapper(accountId, api)
        dao.insert(db)
    }

    override suspend fun editOperation(
        accountId: String,
        operation: Operation
    ) {
        val api = operationDomainToApiMapper(operation)
        retrofitErrorHandler(service.editOperation(accountId, api))
        val db = operationApiToDbMapper(accountId, api)
        dao.insert(db)
    }

    override suspend fun deleteOperation(accountId: String, operationId: String) {
        retrofitErrorHandler(service.deleteOperation(accountId, operationId))
        dao.delete(operationId)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun getOperation(operationId: String): Operation? {
        val db = dao.getOperation(operationId)
        return db?.let { operationDbToDomainMapper(it) }
    }

    override fun getOperations(accountId: String): Flow<List<Operation>> = flow {
        emit(dao.getAll().sortedByDescending(OperationDb::date).map(operationDbToDomainMapper))
        dao.deleteAll()
        val api = retrofitErrorHandler(service.getOperations(accountId))
        val db = api.map { operationApiToDbMapper(accountId, it) }
        dao.insertAll(db)
        emitAll(dao.observe().map { it.sortedByDescending(OperationDb::date).map(operationDbToDomainMapper) })
    }
}