package app.futured.academyproject.ui.screens.culture

import app.futured.academyproject.domain.GetCulturalPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CultureViewModel @Inject constructor(
    override val viewState: CultureViewState,
    private val getCulturalPlacesUseCase: GetCulturalPlacesUseCase,
) : BaseViewModel<CultureViewState>(), Culture.Actions {

    init {
        loadCulturalPlaces()
    }

    private fun loadCulturalPlaces() {
        viewState.error = null

        getCulturalPlacesUseCase.execute {
            onSuccess {
                Timber.d("Cultural places: $it")

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
        loadCulturalPlaces()
    }

    override fun navigateToDetailScreen(placeId: Int) {
        sendEvent(NavigateToDetailEvent(placeId))
    }
}
