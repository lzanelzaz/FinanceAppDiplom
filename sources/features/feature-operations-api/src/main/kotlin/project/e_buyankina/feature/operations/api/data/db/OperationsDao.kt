package project.e_buyankina.feature.operations.api.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationsDao {

    @Query("SELECT * FROM OperationDb")
    suspend fun getAll(): List<OperationDb>

    @Query("SELECT * FROM OperationDb")
    fun observe(): Flow<List<OperationDb>>

    @Query("SELECT * FROM OperationDb WHERE operationId = :operationId")
    suspend fun getOperation(operationId: String): OperationDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(operation: OperationDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(operations: List<OperationDb>)

    @Query("DELETE FROM OperationDb WHERE operationId = :operationId")
    suspend fun delete(operationId: String)

    @Query("DELETE FROM OperationDb")
    suspend fun deleteAll()
}