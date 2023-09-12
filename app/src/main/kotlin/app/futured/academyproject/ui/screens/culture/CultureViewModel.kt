package app.futured.academyproject.ui.screens.culture

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.persistence.db.culture.CulturalPlacesRepository
import app.futured.academyproject.data.store.CulturalPlacesStore
import app.futured.academyproject.domain.GetCulturalPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CultureViewModel @Inject constructor(
    override val viewState: CultureViewState,
    private val getCulturalPlacesUseCase: GetCulturalPlacesUseCase,
    private val culturalPlacesStore: CulturalPlacesStore,
    private val culturalPlacesRepository: CulturalPlacesRepository,
) : BaseViewModel<CultureViewState>(), Culture.Actions {

    init {
        loadCulturalPlaces()
    }

    private fun loadCulturalPlaces() {
        viewState.error = null

        getCulturalPlacesUseCase.execute {
            onSuccess { culturalPlaces ->
                Timber.d("Cultural places: $culturalPlaces")
                viewModelScope.launch {
                    culturalPlacesRepository.deleteAll()
                    culturalPlacesRepository.insertCulturalPlaces(culturalPlaces)
                }

                viewState.places = viewState.places.run {
                    clear()
                    addAll(culturalPlaces)
                }
                viewModelScope.launch {
                    culturalPlacesStore.setPlaces(culturalPlaces)
                }
            }
            onError { error ->
                Timber.e(error)
                viewModelScope.launch {
                    val cachedPlaces = withContext(Dispatchers.IO) {
                        culturalPlacesRepository.getAllCulturalPlaces()
                    }
                    if (cachedPlaces.isEmpty()) {
                        viewState.error = error
                    } else {
                        viewState.places = viewState.places.run {
                            clear()
                            addAll(cachedPlaces)
                        }
                        culturalPlacesStore.setPlaces(cachedPlaces)
                    }
                }
            }
        }
    }

    override fun tryAgain() {
        loadCulturalPlaces()
    }

    override fun navigateToDetailScreen(placeId: Int) {
        sendEvent(NavigateToCultureDetailEvent(placeId))
    }
}
