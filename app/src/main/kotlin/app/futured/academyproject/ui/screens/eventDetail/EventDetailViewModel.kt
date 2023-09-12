package app.futured.academyproject.ui.screens.eventDetail

import app.futured.academyproject.domain.GetEventUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    override val viewState: EventDetailViewState,
    private val getEventUseCase: GetEventUseCase,
) : BaseViewModel<EventDetailViewState>(), EventDetail.Actions {

    init {
        loadEvent()
    }

    private fun loadEvent() {
        getEventUseCase.execute(GetEventUseCase.Args(viewState.eventId)) {
            onSuccess { event ->
                viewState.event = event
            }
            onError { error ->
                Timber.e(error)
            }
        }
    }

    override fun navigateBack() {
        sendEvent(NavigateBackEvent)
    }

    override fun navigateToWebsite(url: String) {
        sendEvent(NavigateToWebsiteEvent(url))
    }
}