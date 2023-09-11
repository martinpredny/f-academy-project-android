package app.futured.academyproject.ui.screens.tourism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.ErrorComposable
import app.futured.academyproject.ui.components.LoadingComposable
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.components.TouristPlaceCard
import app.futured.academyproject.ui.theme.Grid
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TourismScreen(
    navigation: NavigationDestinations,
    paddings: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: TourismViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToTourismDetailEvent> {
                navigation.navigateToTourismDetailScreen(placeId = it.placeId)
            }
        }

        Tourism.Content(
            viewModel,
            viewState.places,
            viewState.error,
            paddings,
            modifier,
        )
    }
}

object Tourism {

    interface Actions {

        fun navigateToTourismDetailScreen(placeId: Int) = Unit

        fun tryAgain() = Unit
    }

    object PreviewActions : Actions

    @Composable
    fun Content(
        actions: Actions,
        touristPlaces: PersistentList<TouristPlace>,
        error: Throwable?,
        paddings: PaddingValues,
        modifier: Modifier = Modifier,
    ) {
        when {
            error != null -> {
                ErrorComposable(onTryAgain = actions::tryAgain)
            }

            touristPlaces.isEmpty() -> {
                LoadingComposable()
            }

            touristPlaces.isNotEmpty() -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddings,
                    verticalArrangement = Arrangement.spacedBy(Grid.d1),
                    modifier = modifier
                        .fillMaxSize(),
                ) {
                    items(touristPlaces) { place ->
                        TouristPlaceCard(
                            touristPlace = place,
                            onClick = actions::navigateToTourismDetailScreen,
                        )
                    }
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun TourismContentWithErrorPreview() {
    Showcase {
        Tourism.Content(
            Tourism.PreviewActions,
            touristPlaces = persistentListOf(),
            error = IllegalStateException("Test"),
            paddings = PaddingValues(),
        )
    }
}

@ScreenPreviews
@Composable
private fun TourismContentWithLoadingPreview() {
    Showcase {
        Tourism.Content(
            Tourism.PreviewActions,
            touristPlaces = persistentListOf(),
            error = null,
            paddings = PaddingValues(),
        )
    }
}

