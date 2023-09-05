package app.futured.academyproject.ui.screens.tourismDetail

import app.futured.arkitekt.core.event.Event

sealed class TourismDetailEvents : Event<TourismDetailViewState>()
object NavigateBackEvent : TourismDetailEvents()
