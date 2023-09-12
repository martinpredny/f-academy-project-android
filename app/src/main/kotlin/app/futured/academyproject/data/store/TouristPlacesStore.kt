package app.futured.academyproject.data.store

import app.futured.academyproject.data.model.local.TouristPlace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TouristPlacesStore @Inject constructor() {

    private val touristPlaces = MutableStateFlow<List<TouristPlace>?>(mutableListOf())

    suspend fun setPlaces(places: List<TouristPlace>) {
        this.touristPlaces.emit(places)
    }

    fun getPlaces() = touristPlaces.value

    fun getPlacesFlow() = touristPlaces.asStateFlow()

    fun getPlace(placeId: Int) = touristPlaces.value?.find { touristPlace ->
        touristPlace.id == placeId
    }

    suspend fun clear() {
        touristPlaces.emit(null)
    }
}
