package project.e_buyankina.feature.operations.api.domain

import java.math.BigDecimal
import java.time.LocalDate

data class NewOperation(
    val type: TransactionType,
    val amount: BigDecimal,
    val date: LocalDate,
    val subtype: String,
    val description: String?,
)