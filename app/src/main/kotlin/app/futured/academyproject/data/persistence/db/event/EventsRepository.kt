package app.futured.academyproject.data.persistence.db.event

import app.futured.academyproject.data.model.local.Event
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Event] from a given data source.
 */
interface EventsRepository {
    /**
     * Retrieve all the events from the the given data source as flow.
     */
    fun getAllEventsStream(): Flow<List<Event>>

    /**
     * Retrieve all the events from the the given data source.
     */
    fun getAllEvents(): List<Event>

    /**
     * Retrieve an events from the given data source that matches with the [id].
     */
    fun getEventStream(id: Int): Flow<Event?>

    /**
     * Insert event in the data source
     */
    suspend fun insertEvent(event: Event)

    /**
     * Insert events in the data source
     */
    suspend fun insertEvents(events: List<Event>)

    /**
     * Delete event from the data source
     */
    suspend fun deleteEvent(event: Event)

    /**
     * Delete all events from the data source
     */
    suspend fun deleteAll()

    /**
     * Update event in the data source
     */
    suspend fun updateEvent(event: Event)
}