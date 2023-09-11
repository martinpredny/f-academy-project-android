package app.futured.academyproject.domain

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<Event>>() {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun build(args: Unit): List<Event> {
        val events = apiManager.getEvents()
        val eventsList = mutableListOf<Event>()
        for (item in events.features) {
            eventsList.add(
                Event(
                    id = item.properties.id,
                    longitude = item.geometry?.coordinates?.get(0),
                    latitude = item.geometry?.coordinates?.get(1),
                    name = Html.fromHtml(item.properties.name, Html.FROM_HTML_MODE_LEGACY).toString(),
                    webUrl = item.properties.url,
                    email = item.properties.organizerEmail,
                    image1Url = item.properties.firstImage,
                    tickets = item.properties.tickets,
                    category = item.properties.categories,
                    text = Html.fromHtml(item.properties.text ?: "", Html.FROM_HTML_MODE_LEGACY).toString(),
                    ticketsUrl = item.properties.ticketsUrl,
                    startDate = item.properties.dateFrom,
                    endDate = item.properties.dateTo,
                ),
            )
        }
        return eventsList
    }
}
