package app.futured.academyproject.data.store

import app.futured.academyproject.data.model.local.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsStore @Inject constructor() {

    private val events = MutableStateFlow<List<Event>?>(mutableListOf())

    suspend fun setEvents(events: List<Event>) {
        this.events.emit(events)
    }

    fun getEvents() = events.value

    fun getEventsFlow() = events.asStateFlow()

    fun getEvent(eventId: Int) = events.value?.find { event ->
        event.id == eventId
    }

    suspend fun clear() {
        events.emit(null)
    }
}
