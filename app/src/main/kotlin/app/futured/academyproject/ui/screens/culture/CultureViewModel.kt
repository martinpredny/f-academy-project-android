package app.futured.academyproject.ui.screens.culture

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.persistence.Persistence
import app.futured.academyproject.data.persistence.db.culture.CulturalPlacesRepository
import app.futured.academyproject.data.store.CulturalPlacesStore
import app.futured.academyproject.domain.GetCulturalPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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
    private val persistence: Persistence
) : BaseViewModel<CultureViewState>(), Culture.Actions {

    init {
        if (!persistence.cultureLoadedSinceStartup) {
            loadCulturalPlaces()
        } else {
            loadCulturalPlacesFromDb()
        }
    }

    private fun loadCulturalPlaces() {
        viewState.setState(CultureState.Loading)

        getCulturalPlacesUseCase.execute {
            onSuccess { culturalPlaces ->
                persistence.cultureLoadedSinceStartup = true
                Timber.d("Cultural places: $culturalPlaces")
                viewState.setState(CultureState.Success(culturalPlaces.sortedBy { it.name }.toPersistentList()))
                viewModelScope.launch {
                    culturalPlacesStore.setPlaces(culturalPlaces)
                }
            }
            onError { error ->
                Timber.e(error)
                loadCulturalPlacesFromDb(error)
            }
        }
    }

    private fun loadCulturalPlacesFromDb(throwable: Throwable? = null) {
        viewModelScope.launch {
            val cachedPlaces = withContext(Dispatchers.IO) {
                culturalPlacesRepository.getAllCulturalPlaces()
            }
            if (cachedPlaces.isNotEmpty()) {
                viewState.setState(CultureState.Success(cachedPlaces.toPersistentList()))
                culturalPlacesStore.setPlaces(cachedPlaces)
            } else {
                viewState.setState(CultureState.Error(throwable ?: Throwable("No cultural places stored in the DB")))
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
