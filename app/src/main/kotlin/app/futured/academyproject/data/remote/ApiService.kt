package app.futured.academyproject.data.remote

import app.futured.academyproject.data.model.api.CulturalPlaces
import app.futured.academyproject.data.model.api.Events
import app.futured.academyproject.data.model.api.TouristPlaces
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("omi_ok_kulturni_instituce/FeatureServer/0/query?outFields=*&where=1%3D1&f=geojson")
    suspend fun getAllCulturalPlaces(
    ): CulturalPlaces

    @GET
    suspend fun getAllTouristPlaces(
        @Url url: String,
    ): TouristPlaces

    @GET
    suspend fun getAllEvents(
        @Url url: String,
    ): Events
}
