package app.futured.academyproject.ui.screens.website

import app.futured.arkitekt.core.event.Event

sealed class WebsiteEvents : Event<WebsiteViewState>()
object NavigateBackEvent : WebsiteEvents()
