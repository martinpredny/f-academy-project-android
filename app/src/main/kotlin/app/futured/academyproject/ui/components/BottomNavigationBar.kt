package app.futured.academyproject.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.futured.academyproject.data.model.local.menus.BottomNavigationItem
import app.futured.academyproject.navigation.DestinationRoutes.CULTURE
import app.futured.academyproject.navigation.DestinationRoutes.EVENTS
import app.futured.academyproject.navigation.DestinationRoutes.TOURISM
import app.futured.academyproject.navigation.NavigationDestinations

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomNavigationBar(
    //todo: fix warning
    items: List<BottomNavigationItem>,
    navigation: NavigationDestinations,
    modifier: Modifier = Modifier,
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItemIndex = when (navigation.getNavController().currentDestination?.route) {
        CULTURE -> 0
        TOURISM -> 1
        EVENTS -> 2
        else -> 0
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = modifier,
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navigation.getNavController().navigate(item.title) {
                        popUpTo(CULTURE)
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title,
                    )
                },
            )
        }
    }
}