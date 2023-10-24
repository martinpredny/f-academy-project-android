package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.data.model.mappers.toCulturalPlace
import app.futured.academyproject.data.persistence.db.culture.CulturalPlacesRepository
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetCulturalPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
    private val culturalPlacesRepository: CulturalPlacesRepository
) : UseCase<Unit, List<CulturalPlace>>() {

    override suspend fun build(args: Unit): List<CulturalPlace> {
        val places = apiManager.getCulturalPlaces()
        culturalPlacesRepository.deleteAll()
        val res = places.cultureFeatureDtos.map { it.toCulturalPlace() }
        culturalPlacesRepository.insertCulturalPlaces(res)
        return res
    }
}
