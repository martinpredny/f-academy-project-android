package app.futured.academyproject.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.AppBar
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.theme.CustomColor
import app.futured.academyproject.ui.theme.Grid
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeScreen(
    navigation: NavigationDestinations,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToDetailEvent> {
                navigation.navigateToDetailScreen(placeId = it.placeId)
            }
        }

        Home.Content(
            viewModel,
            viewState.places,
        )
    }
}

object Home {

    interface Actions {
        fun navigateToDetailScreen(placeId: Int) = Unit
    }

    object PreviewActions : Actions

    @Composable
    fun Content(
        actions: Actions,
        places: PersistentList<Place>,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = { AppBar(title = "Davidova Kultůromapa", onNavigationIconClick = null) },
            modifier = modifier,
        ) { contentPadding ->
            Box(
                modifier = Modifier.padding(contentPadding),
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(Grid.d4),
                    verticalArrangement = Arrangement.spacedBy(Grid.d4),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        count = places.size,
                        key = { index -> places[index].id },
                    ) { index ->
                        PlaceCard(places[index], actions)
                    }
                }
            }
        }
    }

    @Composable
    private fun PlaceCard(place: Place, actions: Actions) {
        Card(
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    actions.navigateToDetailScreen(place.id)
                },
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Spacer(modifier = Modifier.height(Grid.d10))
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.h3,
                )
                Spacer(modifier = Modifier.height(Grid.d2))
                Text(
                    text = place.type,
                    style = MaterialTheme.typography.body1,
                    color = CustomColor.textSecondary,
                )
            }
        }
    }
}

@ScreenPreviews
@Composable
fun HomeContentPreview() {
    Showcase {
        Home.Content(
            Home.PreviewActions,
            remember { persistentListOf() },
        )
    }
}
