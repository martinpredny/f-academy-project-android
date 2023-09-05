package app.futured.academyproject.ui.screens.tourism

import app.futured.arkitekt.core.event.Event

sealed class TourismEvents : Event<TourismViewState>()

data class NavigateToTourismDetailEvent(val placeId: Int) : TourismEvents()