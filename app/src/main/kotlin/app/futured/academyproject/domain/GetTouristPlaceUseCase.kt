package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.data.store.TouristPlacesStore
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetTouristPlaceUseCase @Inject constructor(
    private val touristPlacesStore: TouristPlacesStore,
) : UseCase<GetTouristPlaceUseCase.Args, TouristPlace>() {

    override suspend fun build(args: Args): TouristPlace =
        touristPlacesStore.getPlace(args.placeId) ?: throw IllegalArgumentException("Place with id ${args.placeId} not found")

    data class Args(val placeId: Int)
}
