package app.futured.academyproject.ui.screens.cultureDetail

import app.futured.arkitekt.core.event.Event

sealed class CultureDetailEvents : Event<CultureDetailViewState>()
object NavigateBackEvent : CultureDetailEvents()
data class NavigateToWebsiteEvent(val url: String) : CultureDetailEvents()