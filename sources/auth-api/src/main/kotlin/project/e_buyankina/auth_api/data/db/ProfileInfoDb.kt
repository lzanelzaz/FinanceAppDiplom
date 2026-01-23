package project.e_buyankina.auth_api.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProfileInfoDb")
class ProfileInfoDb(
    @PrimaryKey val accountId: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
)