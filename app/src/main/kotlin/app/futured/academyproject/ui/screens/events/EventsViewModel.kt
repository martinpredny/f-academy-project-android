package app.futured.academyproject.ui.screens.events

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.store.EventsStore
import app.futured.academyproject.domain.GetEventsUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    override val viewState: EventsViewState,
    private val getEventsUseCase: GetEventsUseCase,
    private val eventsPlacesStore: EventsStore
) : BaseViewModel<EventsViewState>(), Events.Actions {

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewState.error = null

        getEventsUseCase.execute {
            onSuccess {
                Timber.d("Events: $it")

                viewState.events = viewState.events.run {
                    clear()
                    addAll(it)
                }
                viewModelScope.launch {
                    eventsPlacesStore.setEvents(it)
                }
            }
            onError { error ->
                Timber.e(error)
                viewState.error = error
            }
        }
    }

    override fun tryAgain() {
        loadEvents()
    }

    override fun navigateToDetailScreen(eventId: Int) {
        sendEvent(NavigateToEventDetailEvent(eventId))
    }
}
