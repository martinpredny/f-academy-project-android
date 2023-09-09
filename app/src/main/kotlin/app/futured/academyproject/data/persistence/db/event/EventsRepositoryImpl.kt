package app.futured.academyproject.data.persistence.db.event

import app.futured.academyproject.data.model.local.Event
import kotlinx.coroutines.flow.Flow

class EventsRepositoryImpl(private val eventDao: EventDao) : EventsRepository {
    override fun getAllEventsStream(): Flow<List<Event>> = eventDao.getAllEventsAsFlow()

    override fun getAllEvents(): List<Event> = eventDao.getAllEvents()

    override fun getEventStream(id: Int): Flow<Event?> = eventDao.getEvent(id)

    override suspend fun insertEvent(event: Event) = eventDao.insert(event)

    override suspend fun insertEvents(events: List<Event>) = eventDao.insertAll(events)

    override suspend fun deleteEvent(event: Event) = eventDao.delete(event)

    override suspend fun deleteAll() = eventDao.deleteAll()

    override suspend fun updateEvent(event: Event) = eventDao.update(event)
}