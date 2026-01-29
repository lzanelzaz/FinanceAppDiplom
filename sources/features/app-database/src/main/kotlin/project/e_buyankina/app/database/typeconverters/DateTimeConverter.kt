package project.e_buyankina.app.database.typeconverters

import androidx.room.TypeConverter
import org.joda.time.DateTime

internal class DateTimeConverter {

    @TypeConverter
    fun fromLocalDate(date: DateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String): DateTime {
        return DateTime.parse(dateString)
    }
}