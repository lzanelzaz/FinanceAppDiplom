package project.e_buyankina.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDao
import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDb

@Database(entities = [ProfileInfoDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProfileInfoDao(): ProfileInfoDao
}