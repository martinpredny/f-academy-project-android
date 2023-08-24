package app.futured.academyproject.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Tour
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.R
import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.tools.preview.PlacesProvider
import app.futured.academyproject.ui.components.PlaceCard
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.ui.theme.Grid
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
    )

@Composable
fun HomeScreen(
    navigation: NavigationDestinations,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToDetailEvent> {
                navigation.navigateToDetailScreen(placeId = it.placeId)
            }
        }

        Home.Content(
            viewModel,
            viewState.places,
            viewState.error,
        )
    }
}

object Home {

    interface Actions {

        fun navigateToDetailScreen(placeId: Int) = Unit

        fun tryAgain() = Unit
    }

    object PreviewActions : Actions

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        actions: Actions,
        places: PersistentList<Place>,
        error: Throwable?,
        modifier: Modifier = Modifier,
    ) {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val items = listOf(
            BottomNavigationItem(
                title = "Culture",
                selectedIcon = Icons.Filled.AccountBalance,
                unselectedIcon = Icons.Outlined.AccountBalance,
                hasNews = false,
                badgeCount = null
            ),
            BottomNavigationItem(
                title = "Tourism",
                selectedIcon = Icons.Filled.Tour,
                unselectedIcon = Icons.Outlined.Tour,
                hasNews = false,
                badgeCount = null
            ),
            BottomNavigationItem(
                title = "Events",
                selectedIcon = Icons.Filled.Event,
                unselectedIcon = Icons.Outlined.Event,
                hasNews = false,
                badgeCount = null
            )
        )

        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopAppBar(scrollBehavior)
            },
            content = { innerPadding ->
                when {
                    error != null -> {
                        Error(onTryAgain = actions::tryAgain)
                    }
                    places.isEmpty() -> {
                        Loading()
                    }
                    places.isNotEmpty() -> {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = innerPadding,
                            verticalArrangement = Arrangement.spacedBy(Grid.d1),
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            items(places) { place ->
                                PlaceCard(
                                    place = place,
                                    onClick = actions::navigateToDetailScreen,
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                //navController.navigate(item.title)
                            },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (item.badgeCount != null) {
                                            Badge {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        } else if (item.hasNews) {
                                            Badge()
                                        }
                                    },
                                )
                                {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            },
                        )
                    }
                }
            },
        )
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun HomeTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
        LargeTopAppBar(
            title = {
                Text(
                    stringResource(R.string.app_map_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                    )
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(Grid.d1),
            ),
            scrollBehavior = scrollBehavior,
        )
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

@ScreenPreviews
@Composable
private fun HomeContentPreview(@PreviewParameter(PlacesProvider::class) places: PersistentList<Place>) {
    Showcase {
        Home.Content(
            Home.PreviewActions,
            places,
            error = null,
        )
    }
}

@ScreenPreviews
@Composable
private fun HomeContentWithErrorPreview() {
    Showcase {
        Home.Content(
            Home.PreviewActions,
            places = persistentListOf(),
            error = IllegalStateException("Test"),
        )
    }
}

@ScreenPreviews
@Composable
private fun HomeContentWithLoadingPreview() {
    Showcase {
        Home.Content(
            Home.PreviewActions,
            places = persistentListOf(),
            error = null,
        )
    }
}

