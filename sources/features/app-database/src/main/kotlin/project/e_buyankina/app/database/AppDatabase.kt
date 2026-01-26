package project.e_buyankina.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import project.e_buyankina.app.database.typeconverters.BigDecimalConverter
import project.e_buyankina.app.database.typeconverters.LocalDateConverter
import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDao
import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDb
import project.e_buyankina.feature.operations.api.data.db.OperationDb
import project.e_buyankina.feature.operations.api.data.db.OperationsDao
import project.e_buyankina.feature.operations.api.data.db.TransactionTypeConverter

@Database(entities = [ProfileInfoDb::class, OperationDb::class], version = 1, exportSchema = false)
@TypeConverters(
    LocalDateConverter::class,
    BigDecimalConverter::class,
    TransactionTypeConverter::class,
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getProfileInfoDao(): ProfileInfoDao

    abstract fun getOperationsDao(): OperationsDao
}