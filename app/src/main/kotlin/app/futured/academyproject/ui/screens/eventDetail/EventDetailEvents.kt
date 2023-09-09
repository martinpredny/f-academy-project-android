package app.futured.academyproject.ui.screens.eventDetail

import app.futured.arkitekt.core.event.Event

sealed class EventDetailEvents : Event<EventDetailViewState>()
object NavigateBackEvent : EventDetailEvents()
data class NavigateToWebsiteEvent(val url: String) : EventDetailEvents()