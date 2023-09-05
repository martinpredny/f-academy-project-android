package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.data.store.CulturalPlacesStore
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetCulturalPlaceUseCase @Inject constructor(
    private val culturalPlacesStore: CulturalPlacesStore,
) : UseCase<GetCulturalPlaceUseCase.Args, CulturalPlace>() {

    override suspend fun build(args: Args): CulturalPlace =
        culturalPlacesStore.getPlace(args.placeId) ?: throw IllegalArgumentException("Place with id ${args.placeId} not found")

    data class Args(val placeId: Int)
}
