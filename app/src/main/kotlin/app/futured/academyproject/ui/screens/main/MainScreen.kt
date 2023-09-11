package app.futured.academyproject.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.navigation.NavigationDestinationsImpl
import app.futured.academyproject.ui.NavGraph
import app.futured.academyproject.ui.bottomNavBarItems
import app.futured.academyproject.ui.components.BottomNavigationBar
import app.futured.academyproject.ui.components.TopAppBar
import app.futured.academyproject.ui.drawerItems
import app.futured.academyproject.ui.theme.Grid
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navigation: NavigationDestinations = remember { NavigationDestinationsImpl(navController) },
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var shouldShowBottomAndTopBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    shouldShowBottomAndTopBar = when (navBackStackEntry?.destination?.route) {
        "Culture" -> true
        "Tourism" -> true
        "Events" -> true
        else -> false
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        selectedItemIndex = when(navigation.getNavController().currentDestination?.route) {
            "Culture" -> 0
            "Tourism" -> 0
            "Events" -> 0
            "About" -> 1
            else -> 0
        }

        ModalNavigationDrawer(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.displayCutout),
            gesturesEnabled = shouldShowBottomAndTopBar,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(Grid.d4))
                    drawerItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                navigation.getNavController().navigate(item.route)
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    if (shouldShowBottomAndTopBar) {
                        TopAppBar(scrollBehavior, drawerState, scope)
                    }
                },
                content = {
                    NavGraph(navController, navigation, it)
                },
                bottomBar = {
                    if (shouldShowBottomAndTopBar) {
                        BottomNavigationBar(items = bottomNavBarItems, navigation = navigation)
                    }
                },
            )
        }
    }
}