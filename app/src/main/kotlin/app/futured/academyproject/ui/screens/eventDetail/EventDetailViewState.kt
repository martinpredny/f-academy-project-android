package app.futured.academyproject.ui.screens.eventDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.tools.Constants.Args.PLACE_ID
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class EventDetailViewState @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewState {

    val eventId = savedStateHandle.get<Int>(PLACE_ID) ?: throw IllegalArgumentException("Missing eventId argument")

    var event by mutableStateOf<Event?>(null)
}