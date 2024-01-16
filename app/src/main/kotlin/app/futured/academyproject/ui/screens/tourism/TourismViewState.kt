package app.futured.academyproject.ui.screens.tourism

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@ViewModelScoped
class TourismViewState @Inject constructor() : ViewState {
    private var _state: TourismState by mutableStateOf(TourismState.Loading)

    val places: PersistentList<TouristPlace>
        get() = when (val currentState = _state) {
            is TourismState.Success -> currentState.places
            else -> persistentListOf()
        }

    val error: Throwable?
        get() = when (val currentState = _state) {
            is TourismState.Error -> currentState.throwable
            else -> null
        }

    fun setState(newState: TourismState) {
        _state = newState
    }

    fun getState() : TourismState {
        return _state
    }
}

sealed class TourismState {
    data class Success(val places: PersistentList<TouristPlace>) : TourismState()
    data class Error(val throwable: Throwable) : TourismState()
    object Loading : TourismState()
}