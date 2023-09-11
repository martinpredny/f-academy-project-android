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
import app.futured.academyproject.data.model.local.BottomNavigationItem
import app.futured.academyproject.data.model.local.DrawerNavigationItem
import app.futured.academyproject.data.model.local.TabItem

val bottomNavBarItems = listOf(
    BottomNavigationItem(
        title = "Culture",
        selectedIcon = Icons.Filled.AccountBalance,
        unselectedIcon = Icons.Outlined.AccountBalance,
        hasNews = false,
        badgeCount = null,
    ),
    BottomNavigationItem(
        title = "Tourism",
        selectedIcon = Icons.Filled.Tour,
        unselectedIcon = Icons.Outlined.Tour,
        hasNews = false,
        badgeCount = null,
    ),
    BottomNavigationItem(
        title = "Events",
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
        hasNews = false,
        badgeCount = null,
    ),
)

val drawerItems = listOf(
    DrawerNavigationItem(
        title = "Discover",
        selectedIcon = Icons.Filled.Public,
        unselectedIcon = Icons.Outlined.Public,
        route = "Culture"
    ),
    DrawerNavigationItem(
        title = "About",
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        route = "About"
    ),
    DrawerNavigationItem(
        title = "Log In",
        selectedIcon = Icons.Filled.Login,
        unselectedIcon = Icons.Outlined.Login,
        route = "Login"
    ),
)

val tabItems = listOf(
    TabItem(
        title = "Info",
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info
    ),
    TabItem(
        title = "Show On Map",
        selectedIcon = Icons.Filled.Map,
        unselectedIcon = Icons.Outlined.Map
    )
)