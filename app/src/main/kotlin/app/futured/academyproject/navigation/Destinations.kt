package app.futured.academyproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import app.futured.academyproject.tools.Constants.Args.PLACE_ID
import app.futured.academyproject.tools.Constants.Args.URL

typealias DestinationArgumentKey = String
typealias DestinationArgumentValue = String

object DestinationRoutes {
    const val CULTURE = "Culture"
    const val TOURISM = "Tourism"
    const val EVENTS = "Events"
    const val CULTURE_DETAIL = "CultureDetail/{$PLACE_ID}"
    const val TOURISM_DETAIL = "TourismDetail/{$PLACE_ID}"
    const val EVENT_DETAIL = "EventDetail/{$PLACE_ID}"
    const val WEBSITE = "Website/{$URL}"
    const val ABOUT = "About"
    const val LOGIN = "Login"
}

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList(),
) {
    object Culture : Destination(route = DestinationRoutes.CULTURE)
    object Tourism : Destination(route = DestinationRoutes.TOURISM)
    object Events : Destination(route = DestinationRoutes.EVENTS)
    object CultureDetail : Destination(
        route = DestinationRoutes.CULTURE_DETAIL,
        arguments = listOf(
            navArgument(PLACE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        fun buildRoute(placeId: Int): String = route
            .withArgument(PLACE_ID, placeId.toString())
    }

    object TourismDetail : Destination(
        route = DestinationRoutes.TOURISM_DETAIL,
        arguments = listOf(
            navArgument(PLACE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        fun buildRoute(placeId: Int): String = route
            .withArgument(PLACE_ID, placeId.toString())
    }

    object EventDetail : Destination(
        route = DestinationRoutes.EVENT_DETAIL,
        arguments = listOf(
            navArgument(PLACE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        fun buildRoute(placeId: Int): String = route
            .withArgument(PLACE_ID, placeId.toString())
    }

    object Website : Destination(
        route = DestinationRoutes.WEBSITE,
        arguments = listOf(
            navArgument(URL) {
                type = NavType.StringType
            },
        ),
    ) {
        fun buildRoute(url: String): String = route
            .withArgument(URL, url)
    }

    object About : Destination(route = DestinationRoutes.ABOUT)
    object LogIn : Destination(route = DestinationRoutes.LOGIN)
}

/**
 * Registers provided [destination] as a composable in [NavGraphBuilder].
 */
fun NavGraphBuilder.composable(
    destination: Destination,
    content: @Composable (NavBackStackEntry) -> Unit,
) = composable(
    route = destination.route,
    arguments = destination.arguments,
    deepLinks = destination.deepLinks,
    content = content,
)

/**
 * Registers provided [destination] as a dialog in [NavGraphBuilder].
 */
fun NavGraphBuilder.composable(
    destination: Destination,
    dialogProperties: DialogProperties = DialogProperties(),
    content: @Composable (NavBackStackEntry) -> Unit,
) = dialog(
    route = destination.route,
    arguments = destination.arguments,
    deepLinks = destination.deepLinks,
    dialogProperties = dialogProperties,
    content = content,
)

/**
 * Replaces an argument placeholder defined by [key] in
 * route string with value provided in [argument].
 *
 * Example:
 * Route: "emptyScreen/{title}"
 * key: "title"
 * argument: "Hello"
 * Result: "emptyScreen/Hello"
 */
fun String.withArgument(key: DestinationArgumentKey, argument: DestinationArgumentValue?) =
    argument?.let { replace("{$key}", it) } ?: this