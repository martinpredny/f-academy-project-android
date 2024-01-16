package app.futured.academyproject.ui.screens.event

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.persistence.db.event.EventsRepository
import app.futured.academyproject.data.store.EventsStore
import app.futured.academyproject.domain.GetEventsUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    override val viewState: EventsViewState,
    private val getEventsUseCase: GetEventsUseCase,
    private val eventsStore: EventsStore,
    private val eventsRepository: EventsRepository,
) : BaseViewModel<EventsViewState>(), Events.Actions {

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewState.setState(EventsState.Loading)

        getEventsUseCase.execute {
            onSuccess { events ->
                Timber.d("Events: $events")
                viewState.setState(EventsState.Success(events.toPersistentList()))
                viewModelScope.launch {
                    eventsStore.setEvents(events)
                }
            }
            onError { error ->
                Timber.e(error)
                viewModelScope.launch {
                    val cachedEvents = withContext(Dispatchers.IO) {
                        eventsRepository.getAllEvents()
                    }
                    if (cachedEvents.isNotEmpty()) {
                        viewState.setState(EventsState.Success(cachedEvents.toPersistentList()))
                        eventsStore.setEvents(cachedEvents)
                    } else {
                        viewState.setState(EventsState.Error(error))
                    }
                }
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
