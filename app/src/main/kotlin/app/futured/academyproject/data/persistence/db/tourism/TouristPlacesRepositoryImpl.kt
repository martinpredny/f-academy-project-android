package app.futured.academyproject.data.persistence.db.tourism

import app.futured.academyproject.data.model.local.TouristPlace
import kotlinx.coroutines.flow.Flow

class TouristPlacesRepositoryImpl(private val touristPlaceDao: TouristPlaceDao) : TouristPlacesRepository {
    override fun getAllTouristPlacesStream(): Flow<List<TouristPlace>> = touristPlaceDao.getAllTouristPlacesAsFlow()

    override fun getAllTouristPlaces(): List<TouristPlace> = touristPlaceDao.getAllTouristPlaces()

    override fun getTouristPlaceStream(id: Int): Flow<TouristPlace?> = touristPlaceDao.getTouristPlace(id)

    override suspend fun insertTouristPlace(touristPlace: TouristPlace) = touristPlaceDao.insert(touristPlace)

    override suspend fun insertTouristPlaces(touristPlace: List<TouristPlace>) = touristPlaceDao.insertAll(touristPlace)

    override suspend fun deleteTouristPlace(touristPlace: TouristPlace) = touristPlaceDao.delete(touristPlace)

    override suspend fun deleteAll() = touristPlaceDao.deleteAll()

    override suspend fun updateTouristPlace(touristPlace: TouristPlace) = touristPlaceDao.update(touristPlace)
}