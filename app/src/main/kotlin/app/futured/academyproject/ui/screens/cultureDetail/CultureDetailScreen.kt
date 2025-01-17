package app.futured.academyproject.ui.screens.cultureDetail

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.R
import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.RowTitleValue
import app.futured.academyproject.ui.components.RowTitleValueEmail
import app.futured.academyproject.ui.components.RowTitleValuePhone
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.components.WebsiteSection
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
fun CultureDetailScreen(
    navigation: NavigationDestinations,
    modifier: Modifier = Modifier,
    viewModel: CultureDetailViewModel = hiltViewModel(),
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

        CultureDetail.Content(
            this,
            viewState.culturalPlace,
            modifier,
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
                    title = {
                        Text(
                            text = stringResource(R.string.cultural_place_detail),
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
            culturalPlace?.let { place ->
                TabLayout(culturalPlace = place, contentPadding = contentPadding)
            }

        }
    }
}

@Composable
fun TabLayout(
    culturalPlace: CulturalPlace,
    contentPadding: PaddingValues,
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
            0 -> InfoTab(culturalPlace = culturalPlace)
            1 -> MapTab(culturalPlace = culturalPlace)
        }
    }
}

@Composable
fun InfoTab(
    culturalPlace: CulturalPlace,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        BasicInfoSection(culturalPlace)
        culturalPlace.webUrl?.let { WebsiteSection(it, context) }
        ContactInfoSection(culturalPlace.email, culturalPlace.phone, context)
        BrnoPassSection(culturalPlace.brnoPass)
        ImageSection(culturalPlace.imageUrl)
    }
}

@Composable
fun BasicInfoSection(
    culturalPlace: CulturalPlace
) {
    RowTitleValue(title = stringResource(R.string.name_title), value = culturalPlace.name)
    RowTitleValue(title = stringResource(R.string.type_title), value = culturalPlace.type)
    culturalPlace.street?.let { RowTitleValue(title = stringResource(R.string.street_title), value = it) }
    culturalPlace.streetNumber?.let { RowTitleValue(title = stringResource(R.string.street_number_title), value = it) }
}

@Composable
fun ContactInfoSection(
    email: String?,
    phone: String?,
    context: Context
) {
    email?.let { RowTitleValueEmail(title = stringResource(R.string.email_title), email = it, context = context) }
    phone?.let { RowTitleValuePhone(title = stringResource(R.string.phone_title), phoneNumber = it, context = context) }
}

@Composable
fun BrnoPassSection(
    brnoPass: String?
) {
    brnoPass?.let { RowTitleValue(title = stringResource(R.string.accepts_brno_pass_title), value = acceptsBrnoPass(it)) }
}

@Composable
fun ImageSection(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
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
                        .placeholder(R.drawable.no_image_placeholder)
                        .error(R.drawable.no_image_placeholder)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f),
            )
        }
    }
}

@Composable
fun MapTab(
    culturalPlace: CulturalPlace,
    modifier: Modifier = Modifier,
) {
    val placePosition = LatLng(culturalPlace.latitude!!, culturalPlace.longitude!!)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(placePosition, 15f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(position = placePosition),
            title = culturalPlace.name,
            snippet = culturalPlace.street + " " + culturalPlace.streetNumber,
        )
    }
}

fun acceptsBrnoPass(accepts: String): String {
    return if (accepts == "Ano") {
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
