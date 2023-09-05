package app.futured.academyproject.ui.screens.culture

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.tools.preview.CulturalPlacesProvider
import app.futured.academyproject.ui.components.CulturalPlaceCard
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.theme.Grid
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CultureScreen(
    navigation: NavigationDestinations,
    paddings: PaddingValues,
    viewModel: CultureViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToCultureDetailEvent> {
                navigation.navigateToCultureDetailScreen(placeId = it.placeId)
            }
        }

        Culture.Content(
            viewModel,
            viewState.places,
            viewState.error,
            paddings
        )
    }
}

object Culture {

    interface Actions {

        fun navigateToDetailScreen(placeId: Int) = Unit

        fun tryAgain() = Unit
    }

    object PreviewActions : Actions

    @Composable
    fun Content(
        actions: Actions,
        culturalPlaces: PersistentList<CulturalPlace>,
        error: Throwable?,
        paddings: PaddingValues,
        modifier: Modifier = Modifier,
    ) {
        when {
            error != null -> {
                Error(onTryAgain = actions::tryAgain)
            }

            culturalPlaces.isEmpty() -> {
                Loading()
            }

            culturalPlaces.isNotEmpty() -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddings,
                    verticalArrangement = Arrangement.spacedBy(Grid.d1),
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(culturalPlaces) { place ->
                        CulturalPlaceCard(
                            culturalPlace = place,
                            onClick = actions::navigateToDetailScreen,
                        )
                    }
                }
            }
        }
    }


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

@Composable
fun EventsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Events screen")
    }
}

@ScreenPreviews
@Composable
private fun CultureContentPreview(@PreviewParameter(CulturalPlacesProvider::class) places: PersistentList<CulturalPlace>) {
    Showcase {
        Culture.Content(
            Culture.PreviewActions,
            places,
            error = null,
            paddings = PaddingValues()
        )
    }
}

@ScreenPreviews
@Composable
private fun CultureContentWithErrorPreview() {
    Showcase {
        Culture.Content(
            Culture.PreviewActions,
            culturalPlaces = persistentListOf(),
            error = IllegalStateException("Test"),
            paddings = PaddingValues()
        )
    }
}

@ScreenPreviews
@Composable
private fun CultureContentWithLoadingPreview() {
    Showcase {
        Culture.Content(
            Culture.PreviewActions,
            culturalPlaces = persistentListOf(),
            error = null,
            paddings = PaddingValues()
        )
    }
}

