package project.e_buyankina.app.database

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDao
import project.e_buyankina.feature.operations.api.data.db.OperationsDao

val appDatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }
    single<ProfileInfoDao> { get<AppDatabase>().getProfileInfoDao() }
    single<OperationsDao> { get<AppDatabase>().getOperationsDao() }
}