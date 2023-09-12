@file:Suppress("UnusedPrivateMember")

package app.futured.academyproject.data.remote

import app.futured.academyproject.data.model.api.CulturalPlacesDto
import app.futured.academyproject.data.model.api.EventsDto
import app.futured.academyproject.data.model.api.TouristPlacesDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiManager @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getCulturalPlaces(): CulturalPlacesDto {
        try {
            return apiService.getAllCulturalPlaces()
        } catch (e: Exception) {
            throw ApiExceptionUnknown(message = e.message!!, cause = e.cause)
        }
    }

    suspend fun getTouristPlaces(): TouristPlacesDto {
        try {
            return apiService.getAllTouristPlaces("https://services6.arcgis.com/fUWVlHWZNxUvTUh8/arcgis/rest/services/PLACES/FeatureServer/0/query?outFields=*&where=1%3D1&f=geojson")
        } catch (e: Exception) {
            throw ApiExceptionUnknown(message = e.message!!, cause = e.cause)
        }
    }

    suspend fun getEvents(): EventsDto {
        try {
            return apiService.getAllEvents("https://services6.arcgis.com/fUWVlHWZNxUvTUh8/arcgis/rest/services/Events/FeatureServer/0/query?outFields=*&where=1%3D1&f=geojson")
        } catch (e: Exception) {
            throw ApiExceptionUnknown(message = e.message!!, cause = e.cause)
        }
    }

}
