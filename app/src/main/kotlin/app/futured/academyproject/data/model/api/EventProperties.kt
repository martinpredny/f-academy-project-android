package app.futured.academyproject.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventProperties(
    @SerialName("ID") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("text") val text: String?,
    @SerialName("tickets") val tickets: String?,
    @SerialName("tickets_info") val ticketsInfo: String?,
    @SerialName("images") val images: String?,
    @SerialName("url") val url: String?,
    @SerialName("categories") val categories: String?,
    @SerialName("parent_festivals") val parentFestivals: String?,
    @SerialName("organizer_email") val organizerEmail: String?,
    @SerialName("tickets_url") val ticketsUrl: String?,
    @SerialName("name_en") val nameEn: String?,
    @SerialName("text_en") val textEn: String?,
    @SerialName("url_en") val urlEn: String?,
    @SerialName("tickets_url_en") val ticketsUrlEn: String?,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("date_from") val dateFrom: Long?,
    @SerialName("date_to") val dateTo: Long?,
    @SerialName("first_image") val firstImage: String?,
    @SerialName("ObjectId") val objectId: Int?,
    @SerialName("GlobalID") val globalId: String?
)