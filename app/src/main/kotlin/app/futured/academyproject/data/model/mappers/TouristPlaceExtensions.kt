package app.futured.academyproject.data.model.mappers

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import app.futured.academyproject.data.model.api.TourismFeatureDto
import app.futured.academyproject.data.model.local.TouristPlace

@RequiresApi(Build.VERSION_CODES.N)
fun TourismFeatureDto.toTouristPlace(): TouristPlace {
    return TouristPlace(
        id = this.id,
        longitude = this.geometryDto?.coordinates?.get(0),
        latitude = this.geometryDto?.coordinates?.get(1),
        name = Html.fromHtml(this.properties.name, Html.FROM_HTML_MODE_LEGACY).toString(),
        note = this.properties.text,
        webUrl = this.properties.contactWebsite,
        street = this.properties.addressStreet,
        streetNumber = this.properties.addressStreetNumber,
        email = this.properties.contactEmail,
        phone = this.properties.contactPhone?.replace("&nbsp;", ""),
        imageUrl = this.properties.image,
        text = Html.fromHtml(this.properties.text, Html.FROM_HTML_MODE_LEGACY).toString(),
    )
}