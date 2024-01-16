package app.futured.academyproject.domain

import android.os.Build
import androidx.annotation.RequiresApi
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.data.model.mappers.toTouristPlace
import app.futured.academyproject.data.persistence.db.tourism.TouristPlacesRepository
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetTouristPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
    private val touristPlacesRepository: TouristPlacesRepository
) : UseCase<Unit, List<TouristPlace>>() {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun build(args: Unit): List<TouristPlace> {
        val places = apiManager.getTouristPlaces()
        touristPlacesRepository.deleteAll()
        val res = places.features.map { it.toTouristPlace() }
        touristPlacesRepository.insertTouristPlaces(res)
        return res
    }
}
