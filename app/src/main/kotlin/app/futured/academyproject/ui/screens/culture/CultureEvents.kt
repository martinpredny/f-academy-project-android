package app.futured.academyproject.ui.screens.culture

import app.futured.arkitekt.core.event.Event

sealed class CultureEvents : Event<CultureViewState>()

data class NavigateToDetailEvent(val placeId: Int) : CultureEvents()
