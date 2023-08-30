package app.futured.academyproject.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Tour
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.futured.academyproject.data.model.local.BottomNavigationItem
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.navigation.NavigationDestinationsImpl
import app.futured.academyproject.ui.NavGraph
import app.futured.academyproject.ui.components.BottomNavigationBar
import app.futured.academyproject.ui.components.TopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navigation: NavigationDestinations = remember { NavigationDestinationsImpl(navController) },
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var showBottomAndTopBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomAndTopBar = when (navBackStackEntry?.destination?.route) {
        "Culture" -> true
        "Tourism" -> true
        "Events" -> true
        else -> false
    }

    val items = listOf(
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

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if(showBottomAndTopBar) {
                TopAppBar(scrollBehavior)
            }
        },
        content = {
            NavGraph(navController, navigation, it)
        },
        bottomBar = {
            if(showBottomAndTopBar) {
                BottomNavigationBar(items = items, navigation = navigation)
            }
        },
    )
}