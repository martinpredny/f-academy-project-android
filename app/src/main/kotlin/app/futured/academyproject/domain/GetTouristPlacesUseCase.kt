package app.futured.academyproject.domain

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetTouristPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<TouristPlace>>() {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun build(args: Unit): List<TouristPlace> {
        val places = apiManager.getTouristPlaces()
        val placesList = mutableListOf<TouristPlace>()
        for (item in places.features) {
            placesList.add(
                TouristPlace(
                    id = item.id,
                    longitude = item.geometry?.coordinates?.get(0),
                    latitude = item.geometry?.coordinates?.get(1),
                    name = Html.fromHtml(item.properties.name, Html.FROM_HTML_MODE_LEGACY).toString(),
                    note = item.properties.text,
                    webUrl = item.properties.contactWebsite,
                    street = item.properties.addressStreet,
                    streetNumber = item.properties.addressStreetNumber,
                    email = item.properties.contactEmail,
                    phone = item.properties.contactPhone?.replace("&nbsp;", ""),
                    image1Url = item.properties.image,
                    text = Html.fromHtml(item.properties.text, Html.FROM_HTML_MODE_LEGACY).toString()
                ),
            )
        }
        return placesList
    }
}
