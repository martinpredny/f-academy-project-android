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
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.ErrorState
import app.futured.academyproject.ui.components.LoadingState
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.components.TouristPlaceCard
import app.futured.academyproject.ui.theme.Grid

@Composable
fun TourismScreen(
    navigation: NavigationDestinations,
    paddings: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: TourismViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToTourismDetailEvent> { event ->
                navigation.navigateToTourismDetailScreen(placeId = event.placeId)
            }
        }

        Tourism.Content(
            viewModel,
            viewState.getState(),
            paddings,
            modifier
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
        state: TourismState,
        paddings: PaddingValues,
        modifier: Modifier = Modifier,
    ) {
        when(state) {
            is TourismState.Error -> {
                ErrorState(onTryAgain = actions::tryAgain)
            }
            is TourismState.Loading -> {
                LoadingState()
            }
            is TourismState.Success -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddings,
                    verticalArrangement = Arrangement.spacedBy(Grid.d1),
                    modifier = modifier
                        .fillMaxSize(),
                ) {
                    items(state.places) { place ->
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
            paddings = PaddingValues(),
            state = TourismState.Loading
        )
    }
}

@ScreenPreviews
@Composable
private fun TourismContentWithLoadingPreview() {
    Showcase {
        Tourism.Content(
            Tourism.PreviewActions,
            paddings = PaddingValues(),
            state = TourismState.Loading
        )
    }
}
