package app.futured.academyproject.navigation

import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

interface NavigationDestinations {
    fun popBackStack()
    fun navigateToCulture()
    fun navigateToCultureDetailScreen(placeId: Int)
    fun navigateToTourism()
    fun navigateToTourismDetailScreen(placeId: Int)
    fun navigateToEvents()
    fun navigateToWebsite(url: String)
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
        navController.navigate("Culture")
    }

    override fun navigateToCultureDetailScreen(placeId: Int) =
        navController.navigate(Destination.CultureDetail.buildRoute(placeId))


    override fun navigateToTourism() {
        navController.navigate("Tourism")
    }

    override fun navigateToTourismDetailScreen(placeId: Int) {
        navController.navigate(Destination.TourismDetail.buildRoute(placeId))
    }

    override fun navigateToEvents() {
        navController.navigate("Events")
    }

    override fun navigateToWebsite(url: String) {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
        navController.navigate(Destination.Website.buildRoute(encodedUrl))
    }

    override fun getNavController(): NavController {
        return navController
    }
}
