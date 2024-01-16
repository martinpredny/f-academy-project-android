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
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.ui.components.ErrorState
import app.futured.academyproject.ui.components.EventCard
import app.futured.academyproject.ui.components.LoadingState
import app.futured.academyproject.ui.theme.Grid

@Composable
fun EventsScreen(
    navigation: NavigationDestinations,
    paddings: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToEventDetailEvent> { event ->
                navigation.navigateToEventDetailScreen(eventId = event.eventId)
            }
        }

        Events.Content(
            viewModel,
            viewState.getState(),
            paddings,
            modifier,
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
        state: EventsState,
        paddings: PaddingValues,
        modifier: Modifier = Modifier,
    ) {
        when(state) {
            is EventsState.Error -> {
                ErrorState(onTryAgain = actions::tryAgain)
            }

            is EventsState.Loading -> {
                LoadingState()
            }

            is EventsState.Success -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddings,
                    verticalArrangement = Arrangement.spacedBy(Grid.d1),
                    modifier = modifier
                        .fillMaxSize(),
                ) {
                    items(state.events) { event ->
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
