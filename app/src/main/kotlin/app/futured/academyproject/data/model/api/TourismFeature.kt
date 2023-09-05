package app.futured.academyproject.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TourismFeature(
    @SerialName("id") val id: Int,
    @SerialName("type") val type: String,
    @SerialName("geometry") val geometry: Geometry? = null,
    @SerialName("properties") val properties: TourismProperties,
)