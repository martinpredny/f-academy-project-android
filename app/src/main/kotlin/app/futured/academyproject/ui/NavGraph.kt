package app.futured.academyproject.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.futured.academyproject.navigation.Destination
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.navigation.composable
import app.futured.academyproject.ui.screens.culture.CultureScreen
import app.futured.academyproject.ui.screens.cultureDetail.CultureDetailScreen
import app.futured.academyproject.ui.screens.eventDetail.EventDetailScreen
import app.futured.academyproject.ui.screens.events.EventsScreen
import app.futured.academyproject.ui.screens.tourism.TourismScreen
import app.futured.academyproject.ui.screens.tourismDetail.TourismDetailScreen
import app.futured.academyproject.ui.screens.website.WebsiteScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    navigation: NavigationDestinations,
    paddings: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Culture.route,
    ) {
        composable(Destination.Culture) {
            CultureScreen(navigation, paddings)
        }

        composable(Destination.CultureDetail) {
            CultureDetailScreen(navigation)
        }

        composable(Destination.Tourism) {
            TourismScreen(navigation, paddings)
        }

        composable(Destination.TourismDetail) {
            TourismDetailScreen(navigation)
        }

        composable(Destination.Events) {
            EventsScreen(navigation, paddings)
        }

        composable(Destination.EventDetail) {
            EventDetailScreen(navigation)
        }

        composable(Destination.Website) {
            WebsiteScreen(navigation)
        }
    }
}
