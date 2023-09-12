package app.futured.academyproject.data.remote

import app.futured.academyproject.data.model.api.CulturalPlacesDto
import app.futured.academyproject.data.model.api.EventsDto
import app.futured.academyproject.data.model.api.TouristPlacesDto
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("omi_ok_kulturni_instituce/FeatureServer/0/query?outFields=*&where=1%3D1&f=geojson")
    suspend fun getAllCulturalPlaces(
    ): CulturalPlacesDto

    @GET
    suspend fun getAllTouristPlaces(
        @Url url: String,
    ): TouristPlacesDto

    @GET
    suspend fun getAllEvents(
        @Url url: String,
    ): EventsDto
}
