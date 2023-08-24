package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetCulturalPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<Place>>() {

    override suspend fun build(args: Unit): List<Place> {
        val places = apiManager.getCulturalPlaces()
        val placesList = mutableListOf<Place>()
        for (item in places.features) {
            placesList.add(
                Place(
                    id = item.properties.ogcFid,
                    longitude = item.geometry?.coordinates?.get(0),
                    latitude = item.geometry?.coordinates?.get(1),
                    name = item.properties.name,
                    type = item.properties.type,
                    note = item.properties.note,
                ),
            )
        }
        return placesList
    }
}
