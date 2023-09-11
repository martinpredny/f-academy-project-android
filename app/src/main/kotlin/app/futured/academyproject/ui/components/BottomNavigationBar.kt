package app.futured.academyproject.ui.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import app.futured.academyproject.data.model.local.BottomNavigationItem
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
        "Culture" -> 0
        "Tourism" -> 1
        "Events" -> 2
        else -> 0
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = modifier,
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navigation.getNavController().navigate(item.title)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        },
                    )
                    {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title,
                        )
                    }
                },
            )
        }
    }
}