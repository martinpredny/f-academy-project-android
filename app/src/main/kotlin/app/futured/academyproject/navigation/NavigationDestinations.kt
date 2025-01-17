package app.futured.academyproject.navigation

import androidx.navigation.NavController
import app.futured.academyproject.navigation.DestinationRoutes.ABOUT
import app.futured.academyproject.navigation.DestinationRoutes.CULTURE
import app.futured.academyproject.navigation.DestinationRoutes.EVENTS
import app.futured.academyproject.navigation.DestinationRoutes.TOURISM
import app.futured.academyproject.navigation.DestinationRoutes.LOGIN
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

interface NavigationDestinations {
    fun popBackStack()
    fun navigateToCulture()
    fun navigateToCultureDetailScreen(placeId: Int)
    //todo: use these methods
    fun navigateToTourism()
    fun navigateToTourismDetailScreen(placeId: Int)
    fun navigateToEvents()
    fun navigateToEventDetailScreen(eventId: Int)
    fun navigateToWebsite(url: String)
    fun navigateToAbout()
    fun navigateToLogin()
    fun getNavController(): NavController
}

/**
 * Class that triggers navigation actions on provided [navController].
 */
class NavigationDestinationsImpl(private val navController: NavController) : NavigationDestinations {

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun navigateToCulture() {
        navController.navigate(CULTURE)
    }

    override fun navigateToCultureDetailScreen(placeId: Int) =
        navController.navigate(Destination.CultureDetail.buildRoute(placeId))


    override fun navigateToTourism() {
        navController.navigate(TOURISM)
    }

    override fun navigateToTourismDetailScreen(placeId: Int) {
        navController.navigate(Destination.TourismDetail.buildRoute(placeId))
    }

    override fun navigateToEvents() {
        navController.navigate(EVENTS)
    }

    override fun navigateToEventDetailScreen(eventId: Int) {
        navController.navigate(Destination.EventDetail.buildRoute(eventId))
    }

    override fun navigateToWebsite(url: String) {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
        navController.navigate(Destination.Website.buildRoute(encodedUrl))
    }

    override fun navigateToAbout() {
        navController.navigate(ABOUT)
    }

    override fun navigateToLogin() {
        navController.navigate(LOGIN)
    }

    override fun getNavController(): NavController {
        return navController
    }
}
