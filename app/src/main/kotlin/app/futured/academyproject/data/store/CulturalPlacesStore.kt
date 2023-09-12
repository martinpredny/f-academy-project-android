package app.futured.academyproject.data.store

import app.futured.academyproject.data.model.local.CulturalPlace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CulturalPlacesStore @Inject constructor() {

    //TODO: Mock data for lectures before: "API and data"
    private val culturalPlaces = MutableStateFlow<List<CulturalPlace>?>(mutableListOf())

    suspend fun setPlaces(places: List<CulturalPlace>) {
        this.culturalPlaces.emit(places)
    }

    fun getPlaces() = culturalPlaces.value

    fun getPlacesFlow() = culturalPlaces.asStateFlow()

    fun getPlace(placeId: Int) = culturalPlaces.value?.find { culturalPlace ->
        culturalPlace.id == placeId
    }

    suspend fun clear() {
        culturalPlaces.emit(null)
    }
}
