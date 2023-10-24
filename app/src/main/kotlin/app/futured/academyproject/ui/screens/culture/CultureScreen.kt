package app.futured.academyproject.ui.screens.culture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.tools.preview.CulturalPlacesProvider
import app.futured.academyproject.ui.components.CulturalPlaceCard
import app.futured.academyproject.ui.components.ErrorState
import app.futured.academyproject.ui.components.LoadingState
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.theme.Grid
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CultureScreen(
    navigation: NavigationDestinations,
    paddings: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: CultureViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToCultureDetailEvent> { event ->
                navigation.navigateToCultureDetailScreen(placeId = event.placeId)
            }
        }

        Culture.Content(
            viewModel,
            viewState.places,
            viewState.error,
            paddings,
            modifier,
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
                ErrorState(onTryAgain = actions::tryAgain)
            }

            culturalPlaces.isEmpty() -> {
                LoadingState()
            }

            culturalPlaces.isNotEmpty() -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddings,
                    verticalArrangement = Arrangement.spacedBy(Grid.d1),
                    modifier = modifier
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
}

@ScreenPreviews
@Composable
private fun CultureContentPreview(@PreviewParameter(CulturalPlacesProvider::class) places: PersistentList<CulturalPlace>) {
    Showcase {
        Culture.Content(
            Culture.PreviewActions,
            places,
            error = null,
            paddings = PaddingValues(),
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
            paddings = PaddingValues(),
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
            paddings = PaddingValues(),
        )
    }
}

