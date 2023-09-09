package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<Event>>() {

    override suspend fun build(args: Unit): List<Event> {
        val events = apiManager.getEvents()
        val eventsList = mutableListOf<Event>()
        for (item in events.features) {
            eventsList.add(
                Event(
                    id = item.properties.id,
                    longitude = item.geometry?.coordinates?.get(0),
                    latitude = item.geometry?.coordinates?.get(1),
                    name = item.properties.name,
                    webUrl = item.properties.url,
                    email = item.properties.organizerEmail,
                    image1Url = item.properties.images,
                    tickets = item.properties.tickets,
                    category = item.properties.categories,
                    text = item.properties.text,
                    ticketsUrl = item.properties.ticketsUrl,
                    startDate = item.properties.dateFrom,
                    endDate = item.properties.dateTo
                ),
            )
        }
        return eventsList
    }
}
