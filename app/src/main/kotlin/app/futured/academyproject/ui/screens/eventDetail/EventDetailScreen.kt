package app.futured.academyproject.ui.screens.eventDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.R
import app.futured.academyproject.data.model.local.Event
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
fun EventDetailScreen(
    navigation: NavigationDestinations,
    modifier: Modifier = Modifier,
    viewModel: EventDetailViewModel = hiltViewModel(),
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

        EventDetail.Content(
            this,
            viewState.event,
            modifier,
        )
    }
}

object EventDetail {

    interface Actions {
        fun navigateBack() = Unit
        fun navigateToWebsite(url: String) = Unit
    }

    object PreviewActions : Actions

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        actions: Actions,
        event: Event?,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = (event?.name ?: "Culture Place Detail")) },
                    navigationIcon = {
                        IconButton(onClick = { actions.navigateBack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
            modifier = modifier,
        ) { contentPadding ->
            event?.let { event ->
                TabLayout(event = event, contentPadding = contentPadding, actions = actions)
            }
        }
    }
}

@Composable
fun TabLayout(
    event: Event, contentPadding: PaddingValues,
    actions: EventDetail.Actions,
    modifier: Modifier = Modifier
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
            0 -> InfoTab(event = event, actions = actions)
            1 -> MapTab(event = event)
        }
    }
}

@Composable
fun InfoTab(
    event: Event,
    actions: EventDetail.Actions,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        if (event.category != null) {
            RowTitleValue(title = "Category:", value = event.category)
        }
        if (event.webUrl != null) {
            //Todo: make also composable with clickable url
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Grid.d2, horizontal = Grid.d4),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "Website:", fontWeight = FontWeight.Bold)
                Text(
                    text = event.webUrl,
                    Modifier
                        .clickable {
                            actions.navigateToWebsite(event.webUrl)
                        },
                )
            }
        }
        if (event.email != null) {
            RowTitleValue(title = "Email:", value = event.email)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Grid.d2, horizontal = Grid.d4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Card(
                colors = CardDefaults.cardColors(),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(event.image1Url)
                            .placeholder(R.drawable.no_image_placeholder)
                            .error(R.drawable.no_image_placeholder)
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

@Composable
fun MapTab(
    event: Event,
    modifier: Modifier = Modifier
) {
    val eventPosition = LatLng(event.latitude!!, event.longitude!!)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(eventPosition, 15f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(position = eventPosition),
            title = event.name,
        )
    }
}

@ScreenPreviews
@Composable
fun CultureDetailContentPreview() {
    Showcase {
        EventDetail.Content(
            EventDetail.PreviewActions,
            event = null,
        )
    }
}