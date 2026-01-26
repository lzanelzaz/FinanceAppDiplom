package project.e_buyankina.app.database.typeconverters

import androidx.room.TypeConverter
import java.math.BigDecimal

internal class BigDecimalConverter {

    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal): String {
        return bigDecimal.toString()
    }

    @TypeConverter
    fun toBigDecimal(string: String): BigDecimal {
        return string.toBigDecimal()
    }
}