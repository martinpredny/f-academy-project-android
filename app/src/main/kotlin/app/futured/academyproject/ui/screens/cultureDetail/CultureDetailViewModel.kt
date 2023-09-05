package app.futured.academyproject.ui.screens.cultureDetail

import app.futured.academyproject.domain.GetCulturalPlaceUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CultureDetailViewModel @Inject constructor(
    override val viewState: CultureDetailViewState,
    private val getCulturalPlaceUseCase: GetCulturalPlaceUseCase,
) : BaseViewModel<CultureDetailViewState>(), CultureDetail.Actions {

    init {
        loadPlace()
    }

    private fun loadPlace() {
        getCulturalPlaceUseCase.execute(GetCulturalPlaceUseCase.Args(viewState.placeId)) {
            onSuccess {
                viewState.culturalPlace = it
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
