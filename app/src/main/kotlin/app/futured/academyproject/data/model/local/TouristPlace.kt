package app.futured.academyproject.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tourism")
data class TouristPlace(
    @PrimaryKey
    val id: Int,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val name: String,
    val note: String? = null,
    val webUrl: String? = null,
    val street: String? = null,
    val streetNumber: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val imageUrl: String? = null,
    val text: String? = null,
)
