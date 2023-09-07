package app.futured.academyproject.ui.screens.website

import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebsiteViewModel @Inject constructor(
    override val viewState: WebsiteViewState,
) : BaseViewModel<WebsiteViewState>(), Website.Actions {

    override fun navigateBack() {
        sendEvent(NavigateBackEvent)
    }
}