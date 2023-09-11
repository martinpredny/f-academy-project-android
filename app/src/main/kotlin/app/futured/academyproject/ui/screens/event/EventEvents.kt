package app.futured.academyproject.ui.screens.event

import app.futured.arkitekt.core.event.Event

sealed class EventEvents : Event<EventsViewState>()

data class NavigateToEventDetailEvent(val eventIt: Int) : EventEvents()