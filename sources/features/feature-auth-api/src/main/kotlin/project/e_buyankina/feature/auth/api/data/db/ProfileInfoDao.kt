package project.e_buyankina.feature.auth.api.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileInfoDao {

    @Query("SELECT * FROM ProfileInfoDb LIMIT 1")
    suspend fun getCurrentUser(): ProfileInfoDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileInfoDb)

    @Query("DELETE FROM ProfileInfoDb")
    suspend fun delete()
}