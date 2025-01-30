package com.wemeal.presentation.main.components.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wemeal.data.model.Screen
import com.wemeal.presentation.main.components.BottomIcon

private val items = listOf(
    Screen.Home,
    Screen.Restaurants,
    Screen.Offers,
    Screen.Foodies,
    Screen.MyAccount
)

private fun clearSelections() {
    items.forEach {
        it.selected.value = false
    }
}

@Preview
@Composable
fun PreviewBottomNavigationBar(){
    BottomNavigationBar(rememberNavController())
}

@Composable
fun BottomNavigationBar(navController: NavController){
    androidx.compose.material.BottomNavigation(
        modifier = Modifier.height(56.dp), backgroundColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                selectedContentColor = LocalContentColor.current,
                unselectedContentColor = Color.White,
                icon = {
                    BottomIcon(screen)
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        clearSelections()
                        screen.selected.value = true
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}