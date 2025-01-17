package app.futured.academyproject.data.model.mappers

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import app.futured.academyproject.data.model.api.EventFeatureDto
import app.futured.academyproject.data.model.local.Event

@RequiresApi(Build.VERSION_CODES.N)
fun EventFeatureDto.toEvent(): Event {
    //we need to add http:// to be able to open website
    val webUrl = this.properties.url?.run {
        if (!startsWith("http://") && !startsWith("https://")) {
            "http://$this"
        } else {
            this
        }
    }
    return Event(
        id = this.properties.id,
        longitude = this.geometryDto?.coordinates?.get(0),
        latitude = this.geometryDto?.coordinates?.get(1),
        name = Html.fromHtml(this.properties.name, Html.FROM_HTML_MODE_LEGACY).toString(),
        webUrl = webUrl,
        email = this.properties.organizerEmail,
        imageUrl = this.properties.firstImage,
        tickets = this.properties.tickets,
        category = this.properties.categories,
        text = Html.fromHtml(this.properties.text ?: "", Html.FROM_HTML_MODE_LEGACY).toString(),
        ticketsUrl = this.properties.ticketsUrl,
        startDate = this.properties.dateFrom,
        endDate = this.properties.dateTo,
    )
}