package app.futured.academyproject.data.persistence.db.tourism

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.futured.academyproject.data.model.local.TouristPlace
import kotlinx.coroutines.flow.Flow

@Dao
interface TouristPlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(touristPlace: TouristPlace)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(touristPlaces: List<TouristPlace>)

    @Update
    suspend fun update(touristPlace: TouristPlace)

    @Delete
    suspend fun delete(touristPlace: TouristPlace)

    @Query("DELETE FROM tourism")
    suspend fun deleteAll()

    @Query("SELECT * from tourism WHERE id = :id")
    fun getTouristPlace(id: Int): Flow<TouristPlace>

    @Query("SELECT * from tourism ORDER BY name ASC")
    fun getAllTouristPlacesAsFlow(): Flow<List<TouristPlace>>

    @Query("SELECT * from tourism ORDER BY name ASC")
    fun getAllTouristPlaces(): List<TouristPlace>
}