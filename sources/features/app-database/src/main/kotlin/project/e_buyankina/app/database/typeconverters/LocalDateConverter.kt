package project.e_buyankina.app.database.typeconverters

import androidx.room.TypeConverter
import java.time.LocalDate

internal class LocalDateConverter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }
}