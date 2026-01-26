package project.e_buyankina.feature.operations.api.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import project.e_buyankina.feature.operations.api.domain.TransactionType
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "OperationDb")
class OperationDb(

    @PrimaryKey
    @ColumnInfo("operationId")
    val operationId: String,

    @ColumnInfo("accountId")
    val accountId: String,

    @ColumnInfo("type")
    val type: TransactionType,

    @ColumnInfo("amount")
    val amount: BigDecimal,

    @ColumnInfo("date")
    val date: LocalDate,

    @ColumnInfo("subtype")
    val subtype: String,

    @ColumnInfo("description")
    val description: String? = null,
)