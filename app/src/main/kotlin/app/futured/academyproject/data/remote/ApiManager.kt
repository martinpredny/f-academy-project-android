@file:Suppress("UnusedPrivateMember")

package app.futured.academyproject.data.remote

import app.futured.academyproject.data.model.api.CulturalPlaces
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiManager @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getCulturalPlaces(): CulturalPlaces {
        try {
            return apiService.getAllCulturalPlaces()
        } catch (e: Exception) {
            throw ApiExceptionUnknown(message = e.message!!, cause = e.cause)
        }
    }

}
