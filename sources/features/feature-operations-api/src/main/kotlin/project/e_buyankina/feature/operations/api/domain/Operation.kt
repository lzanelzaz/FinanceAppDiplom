package project.e_buyankina.feature.operations.api.domain

import org.joda.time.DateTime
import java.math.BigDecimal

data class Operation(
    val operationId: String,
    val type: TransactionType,
    val amount: BigDecimal,
    val date: DateTime,
    val subtype: String,
    val description: String?,
)