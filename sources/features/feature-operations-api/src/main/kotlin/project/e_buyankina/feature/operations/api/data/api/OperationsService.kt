package project.e_buyankina.feature.operations.api.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface OperationsService {

    @POST("create_operation")
    suspend fun createOperation(
        @Query("account_id") accountId: String,
        @Body operation: NewOperationApi,
    ): Response<OperationApi>

    @POST("edit_operation")
    suspend fun editOperation(
        @Query("account_id") accountId: String,
        @Body operation: OperationApi,
    )

    @DELETE("delete_operation")
    suspend fun deleteOperation(
        @Query("account_id") accountId: String,
        @Query("operation_id") operationId: String,
    )

    @GET("operations")
    suspend fun getOperations(
        @Query("account_id") accountId: String,
    ): Response<List<OperationApi>>
}
