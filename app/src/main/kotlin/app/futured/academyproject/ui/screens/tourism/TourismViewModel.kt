package app.futured.academyproject.ui.screens.tourism

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.persistence.db.tourism.TouristPlacesRepository
import app.futured.academyproject.data.store.TouristPlacesStore
import app.futured.academyproject.domain.GetTouristPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TourismViewModel @Inject constructor(
    override val viewState: TourismViewState,
    private val getTouristPlacesUseCase: GetTouristPlacesUseCase,
    private val touristPlacesStore: TouristPlacesStore,
    private val touristPlacesRepository: TouristPlacesRepository,
) : BaseViewModel<TourismViewState>(), Tourism.Actions {

    init {
        loadTourismPlaces()
    }

    private fun loadTourismPlaces() {
        viewState.error = null

        getTouristPlacesUseCase.execute {
            onSuccess { touristPlaces ->
                Timber.d("Tourism places: $touristPlaces")
                viewModelScope.launch {
                    touristPlacesRepository.deleteAll()
                    touristPlacesRepository.insertTouristPlaces(touristPlaces)
                }

                viewState.places = viewState.places.run {
                    clear()
                    addAll(touristPlaces)
                }
                viewModelScope.launch {
                    touristPlacesStore.setPlaces(touristPlaces)
                }
            }
            onError { error ->
                Timber.e(error)
                viewModelScope.launch {
                    val cachedPlaces = withContext(Dispatchers.IO) {
                        touristPlacesRepository.getAllTouristPlaces()
                    }
                    if (cachedPlaces.isEmpty()) {
                        viewState.error = error
                    } else {
                        viewState.places = viewState.places.run {
                            clear()
                            addAll(cachedPlaces)
                        }
                        touristPlacesStore.setPlaces(cachedPlaces)
                    }
                }
            }
        }
    }

    override fun tryAgain() {
        loadTourismPlaces()
    }

    override fun navigateToTourismDetailScreen(placeId: Int) {
        sendEvent(NavigateToTourismDetailEvent(placeId))
    }
}
