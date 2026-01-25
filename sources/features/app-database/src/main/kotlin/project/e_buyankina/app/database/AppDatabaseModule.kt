package project.e_buyankina.app.database

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDao

val appDatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }
    single<ProfileInfoDao> { get<AppDatabase>().getProfileInfoDao() }
}