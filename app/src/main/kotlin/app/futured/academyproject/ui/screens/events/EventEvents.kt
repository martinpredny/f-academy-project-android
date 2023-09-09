package app.futured.academyproject.ui.screens.events

import app.futured.arkitekt.core.event.Event

sealed class EventEvents : Event<EventsViewState>()

data class NavigateToEventDetailEvent(val eventIt: Int) : EventEvents()