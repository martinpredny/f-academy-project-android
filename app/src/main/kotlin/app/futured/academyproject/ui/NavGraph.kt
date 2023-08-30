package app.futured.academyproject.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.futured.academyproject.navigation.Destination
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.navigation.composable
import app.futured.academyproject.ui.screens.culture.CultureScreen
import app.futured.academyproject.ui.screens.culture.EventsScreen
import app.futured.academyproject.ui.screens.culture.TourismScreen
import app.futured.academyproject.ui.screens.detail.DetailScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    navigation: NavigationDestinations,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Culture.route,
    ) {
        composable(Destination.Culture) {
            CultureScreen(navigation)
        }

        composable(Destination.Detail) {
            DetailScreen(navigation)
        }

        composable(Destination.Tourism) {
            TourismScreen()
        }
        composable(Destination.Events) {
            EventsScreen()
        }
    }
}
