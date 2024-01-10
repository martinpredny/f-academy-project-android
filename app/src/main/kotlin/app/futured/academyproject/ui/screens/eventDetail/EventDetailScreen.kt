package app.futured.academyproject.ui.screens.eventDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.R
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.RowTitleValue
import app.futured.academyproject.ui.components.RowTitleValueEmail
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.components.WebsiteSection
import app.futured.academyproject.ui.components.ImageSection
import app.futured.academyproject.ui.tabItems
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            onEvent<NavigateToWebsiteEvent> { event ->
                navigation.navigateToWebsite(url = event.url)
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
                    title = {
                        Text(
                            text = stringResource(R.string.event_detail),
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
            event?.let { event ->
                TabLayout(event = event, contentPadding = contentPadding)
            }
        }
    }
}

@Composable
fun TabLayout(
    event: Event, contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
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
            0 -> InfoTab(event = event)
            1 -> MapTab(event = event)
        }
    }
}

@Composable
fun InfoTab(
    event: Event,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        BasicInfoSection(event = event)
        event.webUrl?.let { WebsiteSection(url = it, context = LocalContext.current) }
        DateSection(event = event)
        ImageSection(imageUrl = event.imageUrl, description = event.text)
    }
}

@Composable
private fun BasicInfoSection(
    event: Event
) {
    RowTitleValue(title = stringResource(R.string.name_title), value = event.name)
    if (event.category != null) {
        RowTitleValue(title = stringResource(R.string.category_title), value = event.category)
    }
    if (event.email != null) {
        RowTitleValueEmail(title = stringResource(R.string.email_title), email = event.email, context = LocalContext.current)
    }
}

@Composable
private fun DateSection(
    event: Event
) {
    if (event.startDate != null) {
        val startDate = Date(event.startDate)
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        RowTitleValue(title = stringResource(R.string.start_date_title), value = dateFormat.format(startDate))
    }
    if (event.endDate != null && event.startDate != event.endDate) {
        val endDate = Date(event.endDate)
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        RowTitleValue(title = stringResource(R.string.end_date_title), value = dateFormat.format(endDate))
    }
}

@Composable
fun MapTab(
    event: Event,
    modifier: Modifier = Modifier,
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