package app.futured.academyproject.ui.components

import android.util.Log
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
import app.futured.academyproject.data.model.local.BottomNavigationItem
import app.futured.academyproject.navigation.NavigationDestinations

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    navigation: NavigationDestinations
) {
    var selectedItemIndex by rememberSaveable {
        when(navigation.getNavController().currentDestination?.route) {
            "Culture" -> mutableIntStateOf(0)
            "Tourism" -> mutableIntStateOf(1)
            "Events" -> mutableIntStateOf(2)
            else -> mutableIntStateOf(0)
        }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
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