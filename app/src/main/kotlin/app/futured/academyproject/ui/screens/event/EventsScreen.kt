package app.futured.academyproject.ui.screens.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.ui.components.ErrorComposable
import app.futured.academyproject.ui.components.EventCard
import app.futured.academyproject.ui.components.LoadingComposable
import app.futured.academyproject.ui.theme.Grid
import kotlinx.collections.immutable.PersistentList

@Composable
fun EventsScreen(
    navigation: NavigationDestinations,
    paddings: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = hiltViewModel()
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToEventDetailEvent> {
                navigation.navigateToEventDetailScreen(eventId = it.eventIt)
            }
        }

        Events.Content(
            viewModel,
            viewState.events,
            viewState.error,
            paddings,
            modifier
        )
    }
}

object Events {

    interface Actions {

        fun navigateToDetailScreen(eventId: Int) = Unit

        fun tryAgain() = Unit
    }

    @Composable
    fun Content(
        actions: Actions,
        events: PersistentList<Event>,
        error: Throwable?,
        paddings: PaddingValues,
        modifier: Modifier = Modifier,
    ) {
        when {
            error != null -> {
                ErrorComposable(onTryAgain = actions::tryAgain)
            }

            events.isEmpty() -> {
                LoadingComposable()
            }

            events.isNotEmpty() -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddings,
                    verticalArrangement = Arrangement.spacedBy(Grid.d1),
                    modifier = modifier
                        .fillMaxSize(),
                ) {
                    items(events) { event ->
                        EventCard(
                            event = event,
                            onClick = actions::navigateToDetailScreen,
                        )
                    }
                }
            }
        }
    }
}
