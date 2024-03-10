package ru.hits.hubabank.data.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.hits.hubabank.data.database.user.model.ProfileEntity

@Dao
internal interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profileEntity: ProfileEntity)

    @Query("SELECT * FROM Profile LIMIT 1")
    fun observeProfile(): Flow<ProfileEntity?>

    @Query("DELETE FROM Profile")
    suspend fun deleteProfile()
}
