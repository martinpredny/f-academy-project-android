package app.futured.academyproject.ui.screens.tourismDetail

import app.futured.academyproject.domain.GetTouristPlaceUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TourismDetailViewModel @Inject constructor(
    override val viewState: TourismDetailViewState,
    private val getTouristPlaceUseCase: GetTouristPlaceUseCase,
) : BaseViewModel<TourismDetailViewState>(), TourismDetail.Actions {

    init {
        loadPlace()
    }

    private fun loadPlace() {
        getTouristPlaceUseCase.execute(GetTouristPlaceUseCase.Args(viewState.placeId)) {
            onSuccess {
                viewState.touristPlace = it
            }
            onError {
                Timber.e(it)
            }
        }
    }

    override fun navigateBack() {
        sendEvent(NavigateBackEvent)
    }
}
