package app.futured.academyproject.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Tour
import app.futured.academyproject.data.model.local.menus.BottomNavigationItem
import app.futured.academyproject.data.model.local.menus.DrawerNavigationItem
import app.futured.academyproject.data.model.local.menus.TabItem
import app.futured.academyproject.navigation.DestinationRoutes.ABOUT
import app.futured.academyproject.navigation.DestinationRoutes.CULTURE
import app.futured.academyproject.navigation.DestinationRoutes.LOGIN
import app.futured.academyproject.ui.SectionTitles.ABOUT_TITLE
import app.futured.academyproject.ui.SectionTitles.CULTURE_TITLE
import app.futured.academyproject.ui.SectionTitles.DISCOVER_TITLE
import app.futured.academyproject.ui.SectionTitles.EVENTS_TITLE
import app.futured.academyproject.ui.SectionTitles.INFO_TITLE
import app.futured.academyproject.ui.SectionTitles.LOGIN_TITLE
import app.futured.academyproject.ui.SectionTitles.MAP_TITLE
import app.futured.academyproject.ui.SectionTitles.TOURISM_TITLE

object SectionTitles {
    const val CULTURE_TITLE = "Culture"
    const val TOURISM_TITLE = "Tourism"
    const val EVENTS_TITLE = "Events"
    const val DISCOVER_TITLE = "Discover"
    const val ABOUT_TITLE = "About"
    const val LOGIN_TITLE = "Log In"
    const val INFO_TITLE = "Info"
    const val MAP_TITLE = "Show On Map"
}

val bottomNavBarItems = listOf(
    BottomNavigationItem(
        title = CULTURE_TITLE,
        selectedIcon = Icons.Filled.AccountBalance,
        unselectedIcon = Icons.Outlined.AccountBalance,
    ),
    BottomNavigationItem(
        title = TOURISM_TITLE,
        selectedIcon = Icons.Filled.Tour,
        unselectedIcon = Icons.Outlined.Tour,
    ),
    BottomNavigationItem(
        title = EVENTS_TITLE,
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
    ),
)

val drawerItems = listOf(
    DrawerNavigationItem(
        title = DISCOVER_TITLE,
        selectedIcon = Icons.Filled.Public,
        unselectedIcon = Icons.Outlined.Public,
        route = CULTURE,
    ),
    DrawerNavigationItem(
        title = ABOUT_TITLE,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        route = ABOUT,
    ),
    DrawerNavigationItem(
        title = LOGIN_TITLE,
        selectedIcon = Icons.Filled.Login,
        unselectedIcon = Icons.Outlined.Login,
        route = LOGIN,
    ),
)

val tabItems = listOf(
    TabItem(
        title = INFO_TITLE,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
    ),
    TabItem(
        title = MAP_TITLE,
        selectedIcon = Icons.Filled.Map,
        unselectedIcon = Icons.Outlined.Map,
    ),
)