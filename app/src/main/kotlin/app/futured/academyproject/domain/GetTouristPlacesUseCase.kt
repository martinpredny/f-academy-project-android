package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetTouristPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<TouristPlace>>() {

    override suspend fun build(args: Unit): List<TouristPlace> {
        val places = apiManager.getTouristPlaces()
        val placesList = mutableListOf<TouristPlace>()
        for (item in places.features) {
            placesList.add(
                TouristPlace(
                    id = item.id,
                    longitude = item.geometry?.coordinates?.get(0),
                    latitude = item.geometry?.coordinates?.get(1),
                    name = item.properties.name,
                    image1Url = item.properties.image,
                    streetNumber = item.properties.addressStreet
                ),
            )
        }
        return placesList
    }
}