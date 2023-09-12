package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.data.model.mappers.toCulturalPlace
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetCulturalPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<CulturalPlace>>() {

    override suspend fun build(args: Unit): List<CulturalPlace> {
        val places = apiManager.getCulturalPlaces()
        return places.cultureFeatureDtos.map { it.toCulturalPlace() }
    }
}
