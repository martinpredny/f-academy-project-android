package app.futured.academyproject.data.model.mappers

import app.futured.academyproject.data.model.api.CultureFeatureDto
import app.futured.academyproject.data.model.local.CulturalPlace

fun CultureFeatureDto.toCulturalPlace(): CulturalPlace {
    //we need to add http:// to be able to open website
    val webUrl = this.culturePropertiesDto.webUrl?.run {
        if (!startsWith("http://") && !startsWith("https://")) {
            "http://$this"
        } else {
            this
        }
    }
    return CulturalPlace(
        id = this.culturePropertiesDto.ogcFid,
        longitude = this.geometryDto?.coordinates?.get(0),
        latitude = this.geometryDto?.coordinates?.get(1),
        name = this.culturePropertiesDto.name,
        type = this.culturePropertiesDto.type,
        note = this.culturePropertiesDto.note,
        webUrl = webUrl,
        program = this.culturePropertiesDto.program,
        street = this.culturePropertiesDto.street,
        streetNumber = this.culturePropertiesDto.streetNumber,
        email = this.culturePropertiesDto.email,
        phone = this.culturePropertiesDto.phone,
        nameEN = this.culturePropertiesDto.nameEN,
        noteEN = this.culturePropertiesDto.noteEN,
        accessibilityId = this.culturePropertiesDto.accessibilityId,
        openFrom = this.culturePropertiesDto.openFrom,
        openTo = this.culturePropertiesDto.openTo,
        imageUrl = this.culturePropertiesDto.image1Url,
        brnoPass = this.culturePropertiesDto.brnoPass,
    )
}