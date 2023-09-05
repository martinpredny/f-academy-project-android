package app.futured.academyproject.ui.screens.tourism

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@ViewModelScoped
class TourismViewState @Inject constructor() : ViewState {

    var places: PersistentList<TouristPlace> by mutableStateOf(persistentListOf())

    var error: Throwable? by mutableStateOf(null)
}
