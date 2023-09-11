package app.futured.academyproject.data.model.local

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)