package app.futured.academyproject.data.persistence.db.culture

import app.futured.academyproject.data.model.local.CulturalPlace
import kotlinx.coroutines.flow.Flow

class CulturalPlacesRepositoryImpl(private val culturalPlaceDao: CulturalPlaceDao) : CulturalPlacesRepository {
    override fun getAllCulturalPlacesStream(): Flow<List<CulturalPlace>> = culturalPlaceDao.getAllCulturalPlacesAsFlow()

    override fun getAllCulturalPlaces(): List<CulturalPlace> = culturalPlaceDao.getAllCulturalPlaces()

    override fun getCulturalPlaceStream(id: Int): Flow<CulturalPlace?> = culturalPlaceDao.getCulturalPlace(id)

    override suspend fun insertCulturalPlace(culturalPlace: CulturalPlace) = culturalPlaceDao.insert(culturalPlace)

    override suspend fun insertCulturalPlaces(culturalPlaces: List<CulturalPlace>) = culturalPlaceDao.insertAll(culturalPlaces)

    override suspend fun deleteCulturalPlace(culturalPlace: CulturalPlace) = culturalPlaceDao.delete(culturalPlace)

    override suspend fun deleteAll() = culturalPlaceDao.deleteAll()

    override suspend fun updateCulturalPlace(culturalPlace: CulturalPlace) = culturalPlaceDao.update(culturalPlace)
}