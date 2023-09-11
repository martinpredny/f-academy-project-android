package app.futured.academyproject.ui.screens.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.ui.components.EventCard
import app.futured.academyproject.ui.theme.Grid
import kotlinx.collections.immutable.PersistentList

@Composable
fun EventsScreen(
    navigation: NavigationDestinations,
    paddings: PaddingValues,
    viewModel: EventsViewModel = hiltViewModel(),
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
            paddings
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
                Error(onTryAgain = actions::tryAgain)
            }

            events.isEmpty() -> {
                Loading()
            }

            events.isNotEmpty() -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddings,
                    verticalArrangement = Arrangement.spacedBy(Grid.d1),
                    modifier = Modifier
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

    //todo: extract these, they are repetitive
    @Composable
    private fun Loading() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    private fun Error(
        onTryAgain: () -> Unit,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Grid.d1),
            ) {
                Text(
                    text = "Yups, Error Happened!",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Not our proudest moment. Can you try it again?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
                Button(onClick = onTryAgain) {
                    Text(
                        text = "Try again",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
