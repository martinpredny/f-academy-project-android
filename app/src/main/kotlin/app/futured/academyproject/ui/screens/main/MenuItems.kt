package app.futured.academyproject.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Tour
import app.futured.academyproject.data.model.local.BottomNavigationItem
import app.futured.academyproject.data.model.local.DrawerNavigationItem

val itemsBottomNavBar = listOf(
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

val itemsDrawer = listOf(
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
        route = "Tourism"
    ),
    DrawerNavigationItem(
        title = "Log In",
        selectedIcon = Icons.Filled.Login,
        unselectedIcon = Icons.Outlined.Login,
        route = "Events"
    ),
)