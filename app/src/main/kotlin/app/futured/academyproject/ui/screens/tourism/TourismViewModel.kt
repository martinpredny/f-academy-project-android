package app.futured.academyproject.ui.screens.tourism

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.store.TouristPlacesStore
import app.futured.academyproject.domain.GetTouristPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TourismViewModel @Inject constructor(
    override val viewState: TourismViewState,
    private val getTouristPlacesUseCase: GetTouristPlacesUseCase,
    private val tourismPlacesStore: TouristPlacesStore
) : BaseViewModel<TourismViewState>(), Tourism.Actions {

    init {
        loadTourismPlaces()
    }

    private fun loadTourismPlaces() {
        viewState.error = null

        getTouristPlacesUseCase.execute {
            onSuccess {
                Timber.d("Tourism places: $it")

                viewState.places = viewState.places.run {
                    clear()
                    addAll(it)
                }
                viewModelScope.launch {
                    tourismPlacesStore.setPlaces(it)
                }
            }
            onError { error ->
                Timber.e(error)
                viewState.error = error
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
