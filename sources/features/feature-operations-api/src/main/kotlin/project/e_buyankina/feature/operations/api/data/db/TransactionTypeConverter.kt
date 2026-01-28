package project.e_buyankina.feature.operations.api.data.db

import androidx.room.TypeConverter
import project.e_buyankina.feature.operations.api.domain.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.serialName
    }

    @TypeConverter
    fun toTransactionType(serialName: String): TransactionType {
        return TransactionType.entries.first { it.serialName == serialName }
    }
}