package app.futured.academyproject.ui.screens.tourismDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.R
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.RowTitleValue
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.tabItems
import app.futured.academyproject.ui.theme.Grid
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TourismDetailScreen(
    navigation: NavigationDestinations,
    modifier: Modifier = Modifier,
    viewModel: TourismDetailViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        TourismDetail.Content(
            this,
            viewState.touristPlace,
            modifier,
        )
    }
}

object TourismDetail {

    interface Actions {
        fun navigateBack() = Unit
    }

    object PreviewActions : Actions

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        actions: Actions,
        touristPlace: TouristPlace?,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.tourist_place_detail),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { actions.navigateBack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
            modifier = modifier,
        ) { contentPadding ->
            touristPlace?.let { touristPlace ->
                TabLayout(touristPlace = touristPlace, contentPadding = contentPadding, actions = actions)
            }
        }
    }
}

@Composable
fun TabLayout(
    touristPlace: TouristPlace,
    contentPadding: PaddingValues,
    actions: TourismDetail.Actions,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding),
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    text = { Text(tabItem.title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) {
                                tabItem.selectedIcon
                            } else {
                                tabItem.unselectedIcon
                            },
                            contentDescription = null,
                        )
                    },
                )
            }
        }
        when (selectedTabIndex) {
            0 -> InfoTab(touristPlace = touristPlace, actions = actions)
            1 -> MapTab(touristPlace = touristPlace)
        }
    }
}

@Composable
fun InfoTab(
    touristPlace: TouristPlace,
    actions: TourismDetail.Actions,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        RowTitleValue(title = stringResource(R.string.name_title), value = touristPlace.name)
        if (touristPlace.street != null) {
            RowTitleValue(title = stringResource(R.string.street_title), value = touristPlace.street)
        }
        if (touristPlace.webUrl != null) {
            RowTitleValue(title = stringResource(R.string.website_title), value = touristPlace.webUrl)
        }
        if (touristPlace.email != null) {
            RowTitleValue(title = stringResource(R.string.email_title), value = touristPlace.email)
        }
        if (touristPlace.phone != null) {
            RowTitleValue(title = stringResource(R.string.phone_title), value = touristPlace.phone)
        }
        Card(
            colors = CardDefaults.cardColors(),
            modifier = Modifier.padding(Grid.d4),
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(touristPlace.image1Url)
                        .placeholder(R.drawable.no_image_placeholder)
                        .error(R.drawable.no_image_placeholder)
                        .crossfade(true)
                        .build(),
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            if(touristPlace.text != null) {
                Text(text = touristPlace.text, modifier = Modifier.padding(Grid.d2), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun MapTab(
    touristPlace: TouristPlace,
    modifier: Modifier = Modifier,
) {
    val placePosition = LatLng(touristPlace.latitude!!, touristPlace.longitude!!)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(placePosition, 15f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(position = placePosition),
            title = touristPlace.name,
            snippet = touristPlace.street,
        )
    }
}

@ScreenPreviews
@Composable
fun TourismDetailContentPreview() {
    Showcase {
        TourismDetail.Content(
            TourismDetail.PreviewActions,
            touristPlace = null,
        )
    }
}
