package app.futured.academyproject.ui.screens.culture

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@ViewModelScoped
class CultureViewState @Inject constructor() : ViewState {
    private var _state: CultureState by mutableStateOf(CultureState.Loading)

    val places: PersistentList<CulturalPlace>
        get() = when (val currentState = _state) {
            is CultureState.Success -> currentState.places
            else -> persistentListOf()
        }

    val error: Throwable?
        get() = when (val currentState = _state) {
            is CultureState.Error -> currentState.error
            else -> null
        }

    fun setState(newState: CultureState) {
        _state = newState
    }

    fun getState(): CultureState {
        return _state
    }
}

sealed class CultureState {
    data class Success(val places: PersistentList<CulturalPlace>) : CultureState()
    data class Error(val error: Throwable) : CultureState()
    object Loading : CultureState()
}