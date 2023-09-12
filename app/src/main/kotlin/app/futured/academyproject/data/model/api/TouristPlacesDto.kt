package app.futured.academyproject.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TouristPlacesDto(
    @SerialName("type") val type: String,
    @SerialName("features") val features: List<TourismFeatureDto>,
)

@Serializable
data class TourismFeatureDto(
    @SerialName("id") val id: Int,
    @SerialName("type") val type: String,
    @SerialName("geometry") val geometryDto: GeometryDto? = null,
    @SerialName("properties") val properties: TourismPropertiesDto,
)

@Serializable
data class TourismPropertiesDto(
    @SerialName("name") val name: String,
    @SerialName("text") val text: String,
    @SerialName("image") val image: String?,
    @SerialName("tickets") val tickets: String?,
    @SerialName("url") val url: String,
    @SerialName("contact_email") val contactEmail: String?,
    @SerialName("contact_phone") val contactPhone: String?,
    @SerialName("contact_website") val contactWebsite: String?,
    @SerialName("address") val address: String?,
    @SerialName("latitude") val latitude: Double?,
    @SerialName("longitude") val longitude: Double?,
    @SerialName("address_zoom") val addressZoom: Int?,
    @SerialName("address_place_id") val addressPlaceId: String?,
    @SerialName("address_street_number") val addressStreetNumber: String?,
    @SerialName("address_street_name") val addressStreetName: String?,
    @SerialName("address_street_name_short") val addressStreetNameShort: String?,
    @SerialName("address_state") val addressState: String?,
    @SerialName("address_post_code") val addressPostCode: String?,
    @SerialName("address_country") val addressCountry: String?,
    @SerialName("address_country_short") val addressCountryShort: String?,
    @SerialName("address_street") val addressStreet: String?,
    @SerialName("address_city") val addressCity: String?,
    @SerialName("opening_hours") val openingHours: String?,
    @SerialName("ObjectId") val objectId: String?,
    @SerialName("GlobalID") val globalId: String?,
)