package app.futured.academyproject.ui.screens.tourism

import app.futured.academyproject.domain.GetTouristPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TourismViewModel @Inject constructor(
    override val viewState: TourismViewState,
    private val getTouristPlacesUseCase: GetTouristPlacesUseCase,
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

//    override fun navigateToDetailScreen(placeId: Int) {
//        sendEvent(NavigateToDetailEvent(placeId))
//    }
}
