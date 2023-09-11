package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetCulturalPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<CulturalPlace>>() {

    override suspend fun build(args: Unit): List<CulturalPlace> {
        val places = apiManager.getCulturalPlaces()
        val placesList = mutableListOf<CulturalPlace>()
        for (item in places.cultureFeatures) {
            placesList.add(
                CulturalPlace(
                    id = item.cultureProperties.ogcFid,
                    longitude = item.geometry?.coordinates?.get(0),
                    latitude = item.geometry?.coordinates?.get(1),
                    name = item.cultureProperties.name,
                    type = item.cultureProperties.type,
                    note = item.cultureProperties.note,
                    webUrl = item.cultureProperties.webUrl,
                    program = item.cultureProperties.program,
                    street = item.cultureProperties.street,
                    streetNumber = item.cultureProperties.streetNumber,
                    email = item.cultureProperties.email,
                    phone = item.cultureProperties.phone,
                    nameEN = item.cultureProperties.nameEN,
                    noteEN = item.cultureProperties.noteEN,
                    accessibilityId = item.cultureProperties.accessibilityId,
                    openFrom = item.cultureProperties.openFrom,
                    openTo = item.cultureProperties.openTo,
                    image1Url = item.cultureProperties.image1Url,
                    brnoPass = item.cultureProperties.brnoPass,
                ),
            )
        }
        return placesList
    }
}
