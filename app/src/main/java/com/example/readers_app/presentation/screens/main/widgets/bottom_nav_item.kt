package com.example.readers_app.presentation.screens.main.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.readers_app.domain.models.BottomNavItem
import com.example.readers_app.ui.theme.primary

@Composable
fun RowScope.BottomNaviItem(
    item: BottomNavItem,
    currentRoute: String?,
    navController: NavController
) {
    BottomNavigationItem(
        selectedContentColor = primary,
        unselectedContentColor = Color.LightGray,
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label
            )
        },
        selected = currentRoute == item.route,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
    )
}
