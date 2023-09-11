package app.futured.academyproject.ui.screens.tourismDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.tools.Constants.Args.PLACE_ID
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TourismDetailViewState @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewState {
    val placeId = savedStateHandle.get<Int>(PLACE_ID) ?: throw IllegalArgumentException("Missing placeId argument")
    var touristPlace by mutableStateOf<TouristPlace?>(null)
}
