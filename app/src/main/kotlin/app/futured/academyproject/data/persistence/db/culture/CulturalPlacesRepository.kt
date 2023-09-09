package app.futured.academyproject.data.persistence.db.culture

import app.futured.academyproject.data.model.local.CulturalPlace
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [CulturalPlace] from a given data source.
 */
interface CulturalPlacesRepository {
    /**
     * Retrieve all the cultural places from the the given data source as flow.
     */
    fun getAllCulturalPlacesStream(): Flow<List<CulturalPlace>>

    /**
     * Retrieve all the cultural places from the the given data source.
     */
    fun getAllCulturalPlaces(): List<CulturalPlace>

    /**
     * Retrieve a cultural place from the given data source that matches with the [id].
     */
    fun getCulturalPlaceStream(id: Int): Flow<CulturalPlace?>

    /**
     * Insert cultural place in the data source
     */
    suspend fun insertCulturalPlace(culturalPlace: CulturalPlace)

    /**
     * Insert cultural places in the data source
     */
    suspend fun insertCulturalPlaces(culturalPlaces: List<CulturalPlace>)

    /**
     * Delete cultural place from the data source
     */
    suspend fun deleteCulturalPlace(culturalPlace: CulturalPlace)

    /**
     * Delete all cultural places from the data source
     */
    suspend fun deleteAll()

    /**
     * Update cultural place in the data source
     */
    suspend fun updateCulturalPlace(culturalPlace: CulturalPlace)
}