package app.futured.academyproject.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.futured.academyproject.navigation.Destination
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.navigation.composable
import app.futured.academyproject.ui.screens.culture.CultureScreen
import app.futured.academyproject.ui.screens.culture.EventsScreen
import app.futured.academyproject.ui.screens.detail.DetailScreen
import app.futured.academyproject.ui.screens.tourism.TourismScreen

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

        composable(Destination.Detail) {
            DetailScreen(navigation)
        }

        composable(Destination.Tourism) {
            TourismScreen(navigation, paddings)
        }
        composable(Destination.Events) {
            EventsScreen()
        }
    }
}
