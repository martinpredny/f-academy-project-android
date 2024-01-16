package app.futured.academyproject.ui.screens.tourism

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.persistence.db.tourism.TouristPlacesRepository
import app.futured.academyproject.data.store.TouristPlacesStore
import app.futured.academyproject.domain.GetTouristPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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
        viewState.setState(TourismState.Loading)

        getTouristPlacesUseCase.execute {
            onSuccess { touristPlaces ->
                Timber.d("Tourism places: $touristPlaces")
                viewState.setState(TourismState.Success(touristPlaces.toPersistentList()))
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
                    if (cachedPlaces.isNotEmpty()) {
                        viewState.setState(TourismState.Success(cachedPlaces.toPersistentList()))
                        touristPlacesStore.setPlaces(cachedPlaces)
                    } else {
                        viewState.setState(TourismState.Error(error))
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
