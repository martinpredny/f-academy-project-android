package app.futured.academyproject.ui.screens.website

import androidx.lifecycle.SavedStateHandle
import app.futured.academyproject.tools.Constants.Args.URL
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class WebsiteViewState @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewState {
    val url = savedStateHandle.get<String>(URL) ?: throw IllegalArgumentException("Missing url argument")
}