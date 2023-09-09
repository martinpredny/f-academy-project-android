package app.futured.academyproject.data.persistence.db.tourism

import app.futured.academyproject.data.model.local.TouristPlace
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [TouristPlace] from a given data source.
 */
interface TouristPlacesRepository {
    /**
     * Retrieve all the tourist places from the the given data source as flow.
     */
    fun getAllTouristPlacesStream(): Flow<List<TouristPlace>>

    /**
     * Retrieve all the tourist places from the the given data source.
     */
    fun getAllTouristPlaces(): List<TouristPlace>

    /**
     * Retrieve a tourist place from the given data source that matches with the [id].
     */
    fun getTouristPlaceStream(id: Int): Flow<TouristPlace?>

    /**
     * Insert tourist place in the data source
     */
    suspend fun insertTouristPlace(touristPlace: TouristPlace)

    /**
     * Insert tourist places in the data source
     */
    suspend fun insertTouristPlaces(touristPlace: List<TouristPlace>)

    /**
     * Delete tourist place from the data source
     */
    suspend fun deleteTouristPlace(touristPlace: TouristPlace)

    /**
     * Delete all tourist places from the data source
     */
    suspend fun deleteAll()

    /**
     * Update tourist place in the data source
     */
    suspend fun updateTouristPlace(touristPlace: TouristPlace)
}