package app.futured.academyproject.ui.screens.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.academyproject.data.model.local.Event
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@ViewModelScoped
class EventsViewState @Inject constructor() : ViewState {
    private var _state: EventsState by mutableStateOf(EventsState.Success(persistentListOf()))

    val events: PersistentList<Event>
        get() = when (val currentState = _state) {
            is EventsState.Success -> currentState.events
            else -> persistentListOf()
        }

    val error: Throwable?
        get() = when (val currentState = _state) {
            is EventsState.Error -> currentState.error
            else -> null
        }

    fun setState(newState: EventsState) {
        _state = newState
    }

    fun getState() : EventsState {
        return _state
    }
}

sealed class EventsState {
    data class Success(val events: PersistentList<Event>) : EventsState()
    data class Error(val error: Throwable) : EventsState()
    object Loading : EventsState()
}
