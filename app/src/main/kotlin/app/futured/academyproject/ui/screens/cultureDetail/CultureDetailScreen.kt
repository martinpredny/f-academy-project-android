package app.futured.academyproject.ui.screens.cultureDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.Showcase

@Composable
fun CultureDetailScreen(
    navigation: NavigationDestinations,
    viewModel: CultureDetailViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        CultureDetail.Content(
            this,
            viewState.culturalPlace,
        )
    }
}

object CultureDetail {

    interface Actions {
        fun navigateBack() = Unit
    }

    object PreviewActions : Actions

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        actions: Actions,
        culturalPlace: CulturalPlace?,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Culture Detail Screen") },
                    navigationIcon = {
                        IconButton(onClick = { actions.navigateBack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
            modifier = modifier,
        ) { contentPadding ->
            culturalPlace?.let {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                ) {
                    Text(text = culturalPlace.name)
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
fun CultureDetailContentPreview() {
    Showcase {
        CultureDetail.Content(
            CultureDetail.PreviewActions,
            culturalPlace = null,
        )
    }
}
