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
        for (item in places.cultureFeatureDtos) {
            placesList.add(
                CulturalPlace(
                    id = item.culturePropertiesDto.ogcFid,
                    longitude = item.geometryDto?.coordinates?.get(0),
                    latitude = item.geometryDto?.coordinates?.get(1),
                    name = item.culturePropertiesDto.name,
                    type = item.culturePropertiesDto.type,
                    note = item.culturePropertiesDto.note,
                    webUrl = item.culturePropertiesDto.webUrl,
                    program = item.culturePropertiesDto.program,
                    street = item.culturePropertiesDto.street,
                    streetNumber = item.culturePropertiesDto.streetNumber,
                    email = item.culturePropertiesDto.email,
                    phone = item.culturePropertiesDto.phone,
                    nameEN = item.culturePropertiesDto.nameEN,
                    noteEN = item.culturePropertiesDto.noteEN,
                    accessibilityId = item.culturePropertiesDto.accessibilityId,
                    openFrom = item.culturePropertiesDto.openFrom,
                    openTo = item.culturePropertiesDto.openTo,
                    imageUrl = item.culturePropertiesDto.image1Url,
                    brnoPass = item.culturePropertiesDto.brnoPass,
                ),
            )
        }
        return placesList
    }
}
