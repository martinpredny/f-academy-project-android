package app.futured.academyproject.ui.screens.events

import androidx.lifecycle.viewModelScope
import app.futured.academyproject.data.persistence.db.event.EventsRepository
import app.futured.academyproject.data.store.EventsStore
import app.futured.academyproject.domain.GetEventsUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val eventsRepository: EventsRepository
) : BaseViewModel<EventsViewState>(), Events.Actions {

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewState.error = null

        getEventsUseCase.execute {
            onSuccess {
                Timber.d("Events: $it")
                viewModelScope.launch{
                    eventsRepository.deleteAll()
                    eventsRepository.insertEvents(it)
                }

                viewState.events = viewState.events.run {
                    clear()
                    addAll(it)
                }
                viewModelScope.launch {
                    eventsStore.setEvents(it)
                }
            }
            onError { error ->
                Timber.e(error)
                viewModelScope.launch {
                    val cachedEvents = withContext(Dispatchers.IO) {
                        eventsRepository.getAllEvents()
                    }
                    if(cachedEvents.isEmpty()) {
                        viewState.error = error
                    } else {
                        viewState.events = viewState.events.run {
                            clear()
                            addAll(cachedEvents)
                        }
                        eventsStore.setEvents(cachedEvents)
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
