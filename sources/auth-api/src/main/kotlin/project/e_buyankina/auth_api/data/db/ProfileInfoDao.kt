package project.e_buyankina.auth_api.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileInfoDao {

    @Query("SELECT * FROM ProfileInfoDb LIMIT 1")
    fun getCurrentUser(): ProfileInfoDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: ProfileInfoDb)

    @Query("DELETE FROM ProfileInfoDb")
    fun delete()
}