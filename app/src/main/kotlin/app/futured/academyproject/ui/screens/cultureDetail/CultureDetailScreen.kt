package app.futured.academyproject.ui.screens.cultureDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.theme.Grid
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

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
            onEvent<NavigateToWebsiteEvent> {
                navigation.navigateToWebsite(url = it.url)
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
        fun navigateToWebsite(url: String) = Unit
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
                    title = { Text(text = (culturalPlace?.name ?: "Culture Place Detail")) },
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
                    modifier = Modifier
                        .padding(contentPadding)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Grid.d2, horizontal = Grid.d4),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Name:", fontWeight = FontWeight.Bold)
                        Text(
                            text = culturalPlace.name
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Grid.d2, horizontal = Grid.d4),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Type:", fontWeight = FontWeight.Bold)
                        Text(
                            text = culturalPlace.type
                        )
                    }
                    if(culturalPlace.street != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Grid.d2, horizontal = Grid.d4),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Street:", fontWeight = FontWeight.Bold)
                            Text(
                                text = culturalPlace.street
                            )
                        }
                    }
                    if(culturalPlace.streetNumber != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Grid.d2, horizontal = Grid.d4),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Street Number:", fontWeight = FontWeight.Bold)
                            Text(
                                text = culturalPlace.streetNumber
                            )
                        }
                    }
                    if(culturalPlace.webUrl != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Grid.d2, horizontal = Grid.d4),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Website:", fontWeight = FontWeight.Bold)
                            Text(
                                text = culturalPlace.webUrl, Modifier
                                    .clickable {
                                        actions.navigateToWebsite(culturalPlace.webUrl)
                                    }
                            )
                        }
                    }
                    if(culturalPlace.email != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Grid.d2, horizontal = Grid.d4),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Email:", fontWeight = FontWeight.Bold)
                            Text(
                                text = culturalPlace.email
                            )
                        }
                    }
                    if(culturalPlace.phone != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Grid.d2, horizontal = Grid.d4),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Phone Number:", fontWeight = FontWeight.Bold)
                            Text(
                                text = culturalPlace.phone
                            )
                        }
                    }
                    if(culturalPlace.brnoPass != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Grid.d2, horizontal = Grid.d4),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Accepts Brno Pass:", fontWeight = FontWeight.Bold)
                            Text(
                                text = acceptsBrnoPass(culturalPlace.brnoPass)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Grid.d2, horizontal = Grid.d4),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            colors = CardDefaults.cardColors()
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(culturalPlace.image1Url)
                                        .crossfade(true)
                                        .build(),
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1f),
                            )
                        }
                    }
                }
            }
        }
    }
}

fun acceptsBrnoPass(accepts: String): String {
    return if(accepts == "Ano") {
        "Yes"
    } else {
        "No"
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
