package app.futured.academyproject.data.persistence.db.culture

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.futured.academyproject.data.model.local.CulturalPlace
import kotlinx.coroutines.flow.Flow

@Dao
interface CulturalPlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(culturalPlace: CulturalPlace)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(culturalPlaces: List<CulturalPlace>)

    @Update
    suspend fun update(culturalPlace: CulturalPlace)

    @Delete
    suspend fun delete(culturalPlace: CulturalPlace)

    @Query("DELETE FROM culture")
    suspend fun deleteAll()

    @Query("SELECT * from culture WHERE id = :id")
    fun getCulturalPlace(id: Int): Flow<CulturalPlace>

    @Query("SELECT * from culture ORDER BY name ASC")
    fun getAllCulturalPlacesAsFlow(): Flow<List<CulturalPlace>>

    @Query("SELECT * from culture ORDER BY name ASC")
    fun getAllCulturalPlaces(): List<CulturalPlace>
}