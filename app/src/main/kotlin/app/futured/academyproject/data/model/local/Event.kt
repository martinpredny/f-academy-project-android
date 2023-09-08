package app.futured.academyproject.data.model.local

data class Event(
    val id: Int,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val name: String,
    val webUrl: String? = null,
    val email: String? = null,
    val image1Url: String? = null,
    val tickets: String? = null,
    val category: String? = null,
    val text: String? = null,
    val ticketsUrl: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null
)