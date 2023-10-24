package app.futured.academyproject.domain

import android.os.Build
import androidx.annotation.RequiresApi
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.data.model.mappers.toEvent
import app.futured.academyproject.data.persistence.db.event.EventsRepository
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val apiManager: ApiManager,
    private val eventsRepository: EventsRepository,
) : UseCase<Unit, List<Event>>() {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun build(args: Unit): List<Event> {
        val events = apiManager.getEvents()
        eventsRepository.deleteAll()
        val res = events.features.map { it.toEvent() }
        eventsRepository.insertEvents(res)
        return res
    }
}
