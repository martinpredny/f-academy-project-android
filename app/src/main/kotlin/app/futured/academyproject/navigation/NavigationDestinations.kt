package app.futured.academyproject.navigation

import androidx.navigation.NavController

interface NavigationDestinations {
    fun popBackStack()
    fun navigateToDetailScreen(placeId: Int)
    fun navigateToCulture()
    fun navigateToTourism()
    fun navigateToEvents()
    fun getNavController(): NavController
}

/**
 * Class that triggers navigation actions on provided [navController].
 */
class NavigationDestinationsImpl(private val navController: NavController) : NavigationDestinations {

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun navigateToDetailScreen(placeId: Int) =
        navController.navigate(Destination.Detail.buildRoute(placeId))

    override fun navigateToCulture() {
        navController.navigate("Culture")
    }

    override fun navigateToTourism() {
        navController.navigate("Tourism")
    }

    override fun navigateToEvents() {
        navController.navigate("Events")
    }

    override fun getNavController(): NavController {
        return navController
    }
}
